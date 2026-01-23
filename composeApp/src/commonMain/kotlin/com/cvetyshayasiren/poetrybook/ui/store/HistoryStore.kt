package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.toDatedPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.repository.HistoryRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store

typealias HistoryStoreState = DatedPoemBookmarks

sealed interface HistoryStoreIntent {
    class AddBookmark(val bookmark: Bookmark): HistoryStoreIntent
    class Clear(val index: Int): HistoryStoreIntent
    data object ClearAll: HistoryStoreIntent
}

sealed interface HistoryStoreEffect

class HistoryStateReducer(): Reducer<HistoryStoreState, HistoryStoreIntent, HistoryStoreEffect> {
    override suspend fun reduce(
        state: HistoryStoreState,
        intent: HistoryStoreIntent
    ): ReducerResult<HistoryStoreState, out HistoryStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is HistoryStoreIntent.AddBookmark -> state + intent.bookmark.toDatedPoemBookmark()
            is HistoryStoreIntent.Clear -> {
                val mutableList = state.toMutableList()
                mutableList.removeAt(intent.index)
                mutableList
            }
            is HistoryStoreIntent.ClearAll -> listOf()
        }
    )
}

class HistoryStore(
    repository: HistoryRepository
): Store<HistoryStoreState, HistoryStoreIntent, HistoryStoreEffect> (
    defaultState = listOf(),
    initialiseState = { repository.getHistory() },
    reducer = HistoryStateReducer()
)