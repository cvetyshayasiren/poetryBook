package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import com.cvetyshayasiren.poetrybook.ui.store.utils.StoreLogger

sealed interface PageStoreState {
    data object Loading: PageStoreState
    data class Prepared(val poem: Poem): PageStoreState
}

sealed interface PageStoreIntent {
    data object SwitchRandom: PageStoreIntent
    data object SwitchNext: PageStoreIntent
    data object SwitchPrevious: PageStoreIntent
}

sealed interface PageStoreEffect

class PageStoreReducer(
    val poetryBookStore: PoetryBookStore
): Reducer<PageStoreState, PageStoreIntent, PageStoreEffect> {
    override suspend fun reduce(
        state: PageStoreState,
        intent: PageStoreIntent
    ): ReducerResult<PageStoreState, out PageStoreEffect?> {
        val currentPoem = when(state) {
            PageStoreState.Loading -> poetryBookStore.getRandomPoem()
            is PageStoreState.Prepared -> state.poem
        }
        val bookmark = currentPoem.toBasicPoemBookmark()

        return ReducerResult.build(
            state = PageStoreState.Prepared(
                poem = when(intent) {
                    PageStoreIntent.SwitchNext -> poetryBookStore.getNextPoem(bookmark)
                    PageStoreIntent.SwitchPrevious -> poetryBookStore.getPreviousPoem(bookmark)
                    PageStoreIntent.SwitchRandom -> poetryBookStore.getRandomPoem(currentPoem)
                }
            )
        )
    }
}

class PageStore(
    poetryBookStore: PoetryBookStore
): Store<PageStoreState, PageStoreIntent, PageStoreEffect>(
    defaultState = PageStoreState.Loading,
    initialiseState = {
        PageStoreState.Prepared(poem = poetryBookStore.getRandomPoem())
    },
    reducer = PageStoreReducer(poetryBookStore = poetryBookStore)
)