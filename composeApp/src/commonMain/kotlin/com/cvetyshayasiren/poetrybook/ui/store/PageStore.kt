package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem

typealias PageStoreState = Poem

sealed interface PageStoreIntent {
    data object SwitchRandom: PageStoreIntent
    data object SwitchNext: PageStoreIntent
    data object SwitchPrevious: PageStoreIntent
}

sealed interface PageStoreEffect

//class PageStoreReducer(
//    val poetryBookStore: PoetryBookStore
//): Reducer<PageStoreState, PageStoreIntent, PageStoreEffect> {
//    override suspend fun reduce(
//        state: PageStoreState,
//        intent: PageStoreIntent
//    ): ReducerResult<PageStoreState, out PageStoreEffect?> = ReducerResult.build(
//        state = when(intent) {
//            PageStoreIntent.SwitchNext -> TODO()
//            PageStoreIntent.SwitchPrevious -> TODO()
//            PageStoreIntent.SwitchRandom -> TODO()
//        }
//    )
//}
//
//class PageStore(
//    poetryBookStore: PoetryBookStore
//): Store<PageStoreState, PageStoreIntent, PageStoreEffect>(
//    defaultState = Poem(),
//    initialiseState = {  },
//    reducer = PageStoreReducer(poetryBookStore = poetryBookStore)
//)