package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store

sealed interface PageStoreState {
    data object Loading: PageStoreState
    data class Prepared(
        val poem: Poem,
        val isInFavorites: Boolean
    ): PageStoreState
}

sealed interface PageStoreIntent {
    data object SwitchRandom: PageStoreIntent
    data object SwitchNext: PageStoreIntent
    data object SwitchPrevious: PageStoreIntent
    data object SwitchFavorites: PageStoreIntent
}

sealed interface PageStoreEffect

class PageStoreReducer(
    val poetryBookStore: PoetryBookStore,
    val favoritesStore: FavoritesStore
): Reducer<PageStoreState, PageStoreIntent, PageStoreEffect> {
    override suspend fun reduce(
        state: PageStoreState,
        intent: PageStoreIntent
    ): ReducerResult<PageStoreState, out PageStoreEffect?> {
        val currentPoem = when(state) {
            PageStoreState.Loading -> poetryBookStore.getRandomPoem()
            is PageStoreState.Prepared -> state.poem
        }
        val currentIsInFavorites = when(state) {
            PageStoreState.Loading -> favoritesStore.isInFavorites(currentPoem.toBasicPoemBookmark())
            is PageStoreState.Prepared -> state.isInFavorites
        }

        val bookmark = currentPoem.toBasicPoemBookmark()
        val newPoem = when(intent) {
            PageStoreIntent.SwitchNext -> poetryBookStore.getNextPoem(bookmark)
            PageStoreIntent.SwitchPrevious -> poetryBookStore.getPreviousPoem(bookmark)
            PageStoreIntent.SwitchRandom -> poetryBookStore.getRandomPoem(currentPoem)
            PageStoreIntent.SwitchFavorites -> currentPoem
        }
        val newIsInFavorites = when(intent) {
            PageStoreIntent.SwitchFavorites -> (!currentIsInFavorites).also {
                when(it) {
                    true -> favoritesStore
                        .sendIntent(FavoritesStoreIntent.AddPoem(newPoem.toTitledPoemBookmark()))
                    false -> favoritesStore
                        .sendIntent(FavoritesStoreIntent.DeletePoem(newPoem.toTitledPoemBookmark()))
                }
            }
            else -> favoritesStore.isInFavorites(newPoem.toBasicPoemBookmark())
        }



        return ReducerResult.build(
            state = PageStoreState.Prepared(
                poem = newPoem,
                isInFavorites = newIsInFavorites
            )
        )
    }
}

class PageStore(
    poetryBookStore: PoetryBookStore,
    favoritesStore: FavoritesStore,
): Store<PageStoreState, PageStoreIntent, PageStoreEffect>(
    defaultState = PageStoreState.Loading,
    initialiseState = {
        val randomPoem = poetryBookStore.getRandomPoem()
        val isInFavorites = favoritesStore.isInFavorites(randomPoem.toBasicPoemBookmark())
        PageStoreState.Prepared(poem = randomPoem, isInFavorites = isInFavorites)
    },
    reducer = PageStoreReducer(poetryBookStore = poetryBookStore, favoritesStore = favoritesStore)
)