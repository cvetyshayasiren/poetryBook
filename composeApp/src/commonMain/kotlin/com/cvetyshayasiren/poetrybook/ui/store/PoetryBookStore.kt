package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import com.cvetyshayasiren.poetrybook.domain.repository.PoetryBookRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store

sealed interface PoetryBookState {
    data object Loading: PoetryBookState
    data class Prepared(val book: Poets): PoetryBookState
}

sealed interface PoetryBookIntent {
    class LoadBook: PoetryBookIntent
}

sealed interface PoetryBookEffect

class PoetryBookReducer(
    val repository: PoetryBookRepository
): Reducer<PoetryBookState, PoetryBookIntent, PoetryBookEffect> {
    override suspend fun reduce(
        state: PoetryBookState,
        intent: PoetryBookIntent
    ): ReducerResult<PoetryBookState, out PoetryBookEffect?> = when(intent) {
        is PoetryBookIntent.LoadBook -> ReducerResult.build(
            state = PoetryBookState.Prepared(book = repository.getBook())
        )
    }
}

class PoetryBookStore(
    repository: PoetryBookRepository
): Store<PoetryBookState, PoetryBookIntent, PoetryBookEffect>(
    defaultState = PoetryBookState.Loading,
    initialiseState = { PoetryBookState.Prepared(book = repository.getBook()) },
    reducer = PoetryBookReducer(repository = repository),
    tag = "PoetryBookStore"
)