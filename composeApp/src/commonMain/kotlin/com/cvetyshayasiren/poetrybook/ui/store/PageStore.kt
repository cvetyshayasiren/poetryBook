package com.cvetyshayasiren.poetrybook.ui.store

import androidx.compose.runtime.structuralEqualityPolicy
import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.random
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.contains
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.toBasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.getPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.nextPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.previousPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.randomPoem
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance

sealed interface PageStoreState {
    data object Loading: PageStoreState
    data class Prepared(val page: PageBookmark): PageStoreState
}

sealed interface PageStoreIntent {
    data object SwitchRandom: PageStoreIntent
    data object SwitchNext: PageStoreIntent
    data object SwitchPrevious: PageStoreIntent
    data object SwitchFavorites: PageStoreIntent
    data class NavigateAndSwitch(val bookmark: Bookmark): PageStoreIntent
    data class NavigateToSearch(val selectedPoet: PoetBookmark? = null): PageStoreIntent
}

sealed interface PageStoreEffect

class PageStoreReducer(): Reducer<PageStoreState, PageStoreIntent, PageStoreEffect> {
    override suspend fun reduce(
        state: PageStoreState,
        intent: PageStoreIntent
    ): ReducerResult<PageStoreState, out PageStoreEffect?> = ReducerResult.build {
        val currentPage = when(state) {
            PageStoreState.Loading -> PageBookmark.random()
            is PageStoreState.Prepared -> state.page
        }
        val newPage = when(intent) {
            PageStoreIntent.SwitchRandom -> PageBookmark.random(current = currentPage)
            PageStoreIntent.SwitchNext -> currentPage.nextPage()
            PageStoreIntent.SwitchPrevious -> currentPage.previousPage()
            PageStoreIntent.SwitchFavorites -> currentPage.also { bookmark ->
                val favoritesStore: FavoritesStore by di.instance()
                favoritesStore.sendIntent(intent = FavoritesStoreIntent.SwitchPoem(bookmark))
            }
            is PageStoreIntent.NavigateAndSwitch -> intent.bookmark.toPageBookmark().also {
                val navigationStore: NavigationStore by di.instance()
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
            }
            is PageStoreIntent.NavigateToSearch -> currentPage.also {
                if(intent.selectedPoet != null) {
                    val searchStore: SearchStore by di.instance()
                    searchStore.sendIntent(SearchStoreIntent.SetBook(intent.selectedPoet))
                }
                val navigationStore: NavigationStore by di.instance()
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Search))
            }
        }
        this@build.newState = PageStoreState.Prepared(page = newPage)
    }
}

class PageStore(): Store<PageStoreState, PageStoreIntent, PageStoreEffect>(
    defaultState = PageStoreState.Loading,
    initialiseState = { PageStoreState.Prepared(page = PageBookmark.random()) },
    reducer = PageStoreReducer()
) {
    init {
        launchAfterInit { historyDaemon() }
        launchAfterInit { favoritesDaemon() }
    }

    suspend fun historyDaemon() {
        state.collect { pageStoreState ->
            if(pageStoreState is PageStoreState.Prepared) {
                val historyStore: HistoryStore by di.instance()
                historyStore.sendIntent(HistoryStoreIntent.AddBookmark(pageStoreState.page))
            }
        }
    }

    suspend fun favoritesDaemon() {
        val favoritesStore: FavoritesStore by di.instance()
        favoritesStore.state.collect { favoritesStoreState ->
            updateState { oldState ->
                 when(oldState is PageStoreState.Prepared) {
                    true -> {
                        val bookmark = oldState.page
                        val isInFavorites = favoritesStoreState.contains(bookmark = bookmark)
                        return@updateState oldState.copy(page = bookmark.copy(isInFavorites = isInFavorites))
                    }
                    false -> oldState
                }
            }
        }
    }
}

//models

data class PageBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val poetName: String,
    val title: String,
    val text: String,
    val isInFavorites: Boolean
): Bookmark {

    suspend fun nextPage(): PageBookmark {
        val poetryBookStore: PoetryBookStore by di.instance()
        val book = poetryBookStore.getBook()
        return book.nextPoem(this).toPageBookmark()
    }

    suspend fun previousPage(): PageBookmark {
        val poetryBookStore: PoetryBookStore by di.instance()
        val book = poetryBookStore.getBook()
        return book.previousPoem(this).toPageBookmark()
    }

    companion object {
        suspend fun random(current: Bookmark? = null): PageBookmark {
            val randomStore: RandomStore by di.instance()
            val randomState = randomStore.state.value.randomState
            if(randomState.isNeedStyleChange()) {
                val styleStore: StyleStore by di.instance()
                styleStore.sendIntent(
                    intent = StyleStoreIntent.RandomiseStyle(
                        isRandomiseIsm = randomState.isRandomiseIsm,
                        isRandomiseSeed = randomState.isRandomiseSeed,
                        isRandomiseThemeMode = randomState.isRandomiseThemeMode
                    )
                )
            }

            val poetryBookStore: PoetryBookStore by di.instance()
            val book = poetryBookStore.getBook()
            return when(val behaviour = randomState.randomPoemBehaviour) {
                RandomPoemBehaviour.SamePoet ->
                    book.randomPoem(exclude = current, poetBookmark = current?.toBasicPoetBookmark()).toPageBookmark()
                RandomPoemBehaviour.RandomPoet ->
                    book.randomPoem(exclude = current).toPageBookmark()
                is RandomPoemBehaviour.CertainPoet ->
                    book.randomPoem(exclude = current, poetBookmark = behaviour.poetBookmark).toPageBookmark()
                RandomPoemBehaviour.FromFavorites -> {
                    val favoritesStore: FavoritesStore by di.instance()
                    val state = favoritesStore.state.value
                    state.random(exclude = current).toPageBookmark()
                }
            }
        }
    }
}

suspend fun Poem.toPageBookmark(): PageBookmark {
    val favoritesStore: FavoritesStore by di.instance()
    val state = favoritesStore.state.value
    val isInFavorites = state.contains(bookmark = toBasicPoemBookmark())

    return PageBookmark(
        poetId = poetId,
        poemId = id,
        poetName = poetName,
        title = title,
        text = text,
        isInFavorites = isInFavorites
    )
}

suspend fun Bookmark.toPageBookmark(): PageBookmark {
    val poetryBookStore: PoetryBookStore by di.instance()
    val book = poetryBookStore.getBook()
    val poem = book.getPoem(this)
    return poem.toPageBookmark()
}