package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.poet.*
import com.cvetyshayasiren.poetrybook.domain.repository.PoetryBookRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.flow.first

sealed interface PoetryBookState {
    data object Loading: PoetryBookState
    data class Prepared(val book: Poets): PoetryBookState
}

sealed interface PoetryBookIntent

sealed interface PoetryBookEffect

class PoetryBookReducer: Reducer<PoetryBookState, PoetryBookIntent, PoetryBookEffect> {
    override suspend fun reduce(
        state: PoetryBookState,
        intent: PoetryBookIntent
    ): ReducerResult<PoetryBookState, out PoetryBookEffect?> = ReducerResult.build(state = state)
}

class PoetryBookStore(
    repository: PoetryBookRepository
): Store<PoetryBookState, PoetryBookIntent, PoetryBookEffect>(
    defaultState = PoetryBookState.Loading,
    initialiseState = { PoetryBookState.Prepared(book = repository.getBook()) },
    reducer = PoetryBookReducer()
) {
    suspend fun getBook(): Poets =
        (state.first { poetryBookState -> poetryBookState is PoetryBookState.Prepared }
                as PoetryBookState.Prepared).book
}