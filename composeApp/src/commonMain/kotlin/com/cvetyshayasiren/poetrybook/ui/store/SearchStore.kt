package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.poem.SearchResultPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.SearchResultPoemBookmarks
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreState.HardSearchResult
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreState.SimpleSearchResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.flow.Flow
import org.kodein.di.instance

sealed interface SearchStoreState {
    data object Book: SearchStoreState
    data class SimpleSearchResult(val searchResult: SearchResultPoemBookmarks): SearchStoreState
    data class HardSearchResult(val searchResult: Flow<SearchResultPoemBookmark>): SearchStoreState
}

sealed interface SearchStoreIntent {
    data class SimpleSearchUserInput(val input: String): SearchStoreIntent

    data class HardSearchUSerInput(val input: String): SearchStoreIntent

    data object CancelSearch: SearchStoreIntent
}

sealed interface SearchStoreEffect

class SearchStoreReducer:
    Reducer<SearchStoreState, SearchStoreIntent, SearchStoreEffect> {
    override suspend fun reduce(
        state: SearchStoreState,
        intent: SearchStoreIntent
    ): ReducerResult<SearchStoreState, out SearchStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is SearchStoreIntent.SimpleSearchUserInput -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                SimpleSearchResult(
                    searchResult = poetryBookStore.simpleSearch(intent.input)
                )
            }
            SearchStoreIntent.CancelSearch -> SearchStoreState.Book
            is SearchStoreIntent.HardSearchUSerInput -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                HardSearchResult(
                    searchResult = poetryBookStore.hardSearch(intent.input)
                )
            }
        }
    )
}

class SearchStore: Store<SearchStoreState, SearchStoreIntent, SearchStoreEffect>(
    defaultState = SearchStoreState.Book,
    initialiseState = { SearchStoreState.Book },
    reducer = SearchStoreReducer(),
    tag = "SearchStore"
)