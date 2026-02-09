package com.cvetyshayasiren.poetrybook.ui.store

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Sequence
import com.cvetyshayasiren.poetrybook.domain.models.poem.toBasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import com.cvetyshayasiren.poetrybook.domain.utils.toLinkedHashSet
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreState.HardSearchResult
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreState.SimpleSearchResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kodein.di.instance

sealed interface SearchStoreState {
    data class Book(
        val book: BookSequence,
        val sorting: BookSequenceSorting = BookSequenceSorting.Alphabet,
        val selectedPoet: PoetBookmark? = null
    ): SearchStoreState {
        fun switchSorting(): Book {
            val nextSort = this.sorting.nextSort()
            return Book(book = nextSort.sort(book), sorting = nextSort, selectedPoet = null)
        }
    }
    data class SimpleSearchResult(val searchResult: SearchResultBookmarks): SearchStoreState
    data class HardSearchResult(val searchResult: Flow<SearchResultBookmark>): SearchStoreState

    fun selectedPoetIndex(): Int? = when(this) {
        is Book -> book.value.keys.map { it.toBasicPoetBookmark() }.indexOf(selectedPoet?.toBasicPoetBookmark()).let {
            if(it == -1) null else it
        }
        else -> null
    }
}

sealed interface SearchStoreIntent {
    data object SwitchSorting: SearchStoreIntent
    data class SimpleSearchUserInput(val input: String): SearchStoreIntent
    data class HardSearchUserInput(val input: String): SearchStoreIntent
    data class SetBook(val selectedPoet: PoetBookmark): SearchStoreIntent
    data class HardSearchComplete(val matches: Int): SearchStoreIntent
    data class NavigateToPageAndSwitch(val bookmark: Bookmark): SearchStoreIntent
}

sealed interface SearchStoreEffect {
    data class HardSearchComplete(val matches: Int): SearchStoreEffect
}

class SearchStoreReducer:
    Reducer<SearchStoreState, SearchStoreIntent, SearchStoreEffect> {
    override suspend fun reduce(
        state: SearchStoreState,
        intent: SearchStoreIntent
    ): ReducerResult<SearchStoreState, out SearchStoreEffect?> = ReducerResult.build {
        newState = when(intent) {
            is SearchStoreIntent.SwitchSorting -> {
                when(state is SearchStoreState.Book) {
                    true -> state.switchSorting()
                    false -> state
                }
            }
            is SearchStoreIntent.SimpleSearchUserInput -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                val book = poetryBookStore.getBook()
                SimpleSearchResult(
                    searchResult = book.simpleSearch(intent.input)
                )
            }
            is SearchStoreIntent.SetBook -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                val book = poetryBookStore.getBook()
                SearchStoreState.Book(book.toBookSequence(), selectedPoet = intent.selectedPoet)
            }
            is SearchStoreIntent.HardSearchUserInput -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                val book = poetryBookStore.getBook()
                HardSearchResult(
                    searchResult = book.hardSearch(
                        text = intent.input,
                        onFinish = { matches ->
                            val searchStore: SearchStore by di.instance()
                            searchStore.sendIntent(intent = SearchStoreIntent.HardSearchComplete(matches = matches))
                        }
                    )
                )
            }
            is SearchStoreIntent.HardSearchComplete -> state
            is SearchStoreIntent.NavigateToPageAndSwitch -> state.also {
                val pageStore: PageStore by di.instance()
                pageStore.sendIntent(PageStoreIntent.NavigateAndSwitch(intent.bookmark))
            }
        }
        effect = when(intent) {
            is SearchStoreIntent.HardSearchComplete -> SearchStoreEffect.HardSearchComplete(intent.matches)
            else -> null
        }
    }
}

class SearchStore: Store<SearchStoreState, SearchStoreIntent, SearchStoreEffect>(
    defaultState = SearchStoreState.Book(book = BookSequence(mapOf())),
    initialiseState = {
        val poetryBookStore: PoetryBookStore by di.instance()
        val book = poetryBookStore.getBook()
        SearchStoreState.Book(book = book.toBookSequence())
    },
    reducer = SearchStoreReducer()
)

//models

data class SearchResultBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val poetName: SearchResult,
    val title: SearchResult,
    val text: SearchResult? = null
): Bookmark {
    data class SearchResult(
        val text: String,
        val ranges: List<IntRange>
    ) {
        @Composable
        fun getAnnotatedString(
            commonStyle: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.onSurface),
            accentStyle: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.error),
        ) = buildAnnotatedString {
            val ranges = ranges.flatten()
            text.forEachIndexed { index, char ->
                withStyle(
                    style = when(index in ranges) {
                        true -> accentStyle
                        false -> commonStyle
                    }
                ) {
                    append(char)
                }
            }
        }
    }
}

typealias SearchResultBookmarks = List<SearchResultBookmark>

fun Poets.simpleSearch(text: String): SearchResultBookmarks = buildList {
    val pattern = Regex(pattern = text, option = RegexOption.IGNORE_CASE)
    this@simpleSearch.forEach { poet ->
        poet.poems.forEach { poem ->
            val poetNameRanges = pattern.findAll(poem.poetName).map { it.range }.toList()
            val titleRanges = pattern.findAll(poem.title).map { it.range }.toList()
            if(poetNameRanges.isNotEmpty() || titleRanges.isNotEmpty()) {
                add(
                    SearchResultBookmark(
                        poetId = poet.id,
                        poemId = poem.id,
                        poetName = SearchResultBookmark.SearchResult(
                            text = poem.poetName,
                            ranges = poetNameRanges
                        ),
                        title = SearchResultBookmark.SearchResult(
                            text = poem.title,
                            ranges = titleRanges
                        )
                    )
                )
            }
        }
    }
}

fun Poets.hardSearch(text: String, onFinish: ((Int) -> Unit)? = null): Flow<SearchResultBookmark> = flow {
    var counter = 0
    val pattern = Regex(pattern = text, option = RegexOption.IGNORE_CASE)
    this@hardSearch.forEach { poet ->
        poet.poems.forEach { poem ->
            val poetNameRanges = pattern.findAll(poem.poetName).map { it.range }.toList()
            val titleRanges = pattern.findAll(poem.title).map { it.range }.toList()
            val textRanges = pattern.findAll(poem.text).map { it.range }.toList()
            if(poetNameRanges.isNotEmpty() || titleRanges.isNotEmpty() || textRanges.isNotEmpty()) {
                emit(
                    value = SearchResultBookmark(
                        poetId = poet.id,
                        poemId = poem.id,
                        poetName = SearchResultBookmark.SearchResult(
                            text = poem.poetName,
                            ranges = poetNameRanges
                        ),
                        title = SearchResultBookmark.SearchResult(
                            text = poem.title,
                            ranges = titleRanges
                        ),
                        text = SearchResultBookmark.SearchResult(
                            text = poem.text,
                            ranges = textRanges
                        )
                    )
                )
                counter += 1
            }
        }
    }
    onFinish?.let { it(counter) }
}

//models book

typealias BookSequence = Sequence<BookPoetBookmark, BookBookmark>

data class BookPoetBookmark(
    override val id: Int,
    val name: String
): PoetBookmark

data class BookBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val title: String,
    val isInFavorites: Boolean
): Bookmark

fun Poets.toBookSequence(): BookSequence = BookSequence(
    value = buildMap {
        val favoritesStore: FavoritesStore by di.instance()
        this@toBookSequence.forEach { poet ->
            set(
                key = BookPoetBookmark(id = poet.id, name = poet.name),
                value = poet.poems.map { poem ->
                    BookBookmark(
                        poetId = poem.poetId,
                        poemId = poem.id,
                        title = poem.title,
                        isInFavorites = favoritesStore.contains(poem.toBasicPoemBookmark())
                    )
                }.toLinkedHashSet()
            )
        }
    }
)

sealed interface BookSequenceSorting {
    fun sort(book: BookSequence): BookSequence

    fun nextSort(): BookSequenceSorting = when(this) {
        Alphabet -> Favorite
        Favorite -> Alphabet
    }

    data object Alphabet: BookSequenceSorting {
        override fun sort(book: BookSequence): BookSequence = BookSequence(
            value = buildMap {
                book.value.keys.sortedWith(comparator = compareBy { it.id }).forEach { poetBookmark ->
                    set(
                        key = poetBookmark,
                        value = book.value[poetBookmark]!!.sortedWith(
                            comparator = compareBy { it.poemId }
                        ).toLinkedHashSet()
                    )
                }
            }
        )
    }

    data object Favorite: BookSequenceSorting {
        override fun sort(book: BookSequence): BookSequence = BookSequence(
            value = buildMap {
                book.value.entries
                    .sortedWith(
                        comparator = compareByDescending<Map.Entry<BookPoetBookmark, LinkedHashSet<BookBookmark>>> { entry ->
                            entry.value.count { it.isInFavorites }
                        }.thenBy { entry ->  entry.key.id }
                    )
                    .forEach { (poetBookmark, bookmarks) ->
                        set(
                            key = poetBookmark,
                            value = bookmarks.sortedWith(
                                comparator = compareBy<BookBookmark> { it.isInFavorites }.thenBy { it.poemId }
                            ).toLinkedHashSet()
                        )
                    }
            }
        )
    }
}