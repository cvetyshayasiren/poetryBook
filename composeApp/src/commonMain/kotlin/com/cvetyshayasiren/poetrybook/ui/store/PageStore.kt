package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.random
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.contains
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.toBasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.getPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.nextPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.previousPoem
import com.cvetyshayasiren.poetrybook.domain.models.poet.randomPoem
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
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
    data object RefreshPage: PageStoreIntent
}

sealed interface PageStoreEffect

class PageStoreReducer(): Reducer<PageStoreState, PageStoreIntent, PageStoreEffect> {
    override suspend fun reduce(
        state: PageStoreState,
        intent: PageStoreIntent
    ): ReducerResult<PageStoreState, out PageStoreEffect?> {
        val currentPage = when(state) {
            PageStoreState.Loading -> PageBookmark.random()
            is PageStoreState.Prepared -> state.page
        }
        val newPage = when(intent) {
            PageStoreIntent.SwitchRandom -> PageBookmark.random(current = currentPage)
            PageStoreIntent.RefreshPage -> currentPage.toPageBookmark()
            PageStoreIntent.SwitchNext -> currentPage.nextPage()
            PageStoreIntent.SwitchPrevious -> currentPage.previousPage()
            PageStoreIntent.SwitchFavorites -> {
                val favoritesStore: FavoritesStore by di.instance()
                currentPage.switchFavorites().also { pageBookmark ->
                    favoritesStore.sendIntent(
                        intent = when(pageBookmark.isInFavorites) {
                            true -> FavoritesStoreIntent.AddPoem(pageBookmark)
                            false -> FavoritesStoreIntent.DeletePoem(pageBookmark)
                        }
                    )
                }
            }
        }

        return ReducerResult.build(state = PageStoreState.Prepared(page = newPage))
    }
}

class PageStore(): Store<PageStoreState, PageStoreIntent, PageStoreEffect>(
    defaultState = PageStoreState.Loading,
    initialiseState = { PageStoreState.Prepared(page = PageBookmark.random()) },
    reducer = PageStoreReducer()
)

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

    fun switchFavorites(): PageBookmark = copy(isInFavorites = !isInFavorites)

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
    val isInFavorites = state.contains(value = toBasicPoemBookmark())

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