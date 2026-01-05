package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import com.cvetyshayasiren.poetrybook.domain.repository.RandomStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import com.cvetyshayasiren.poetrybook.ui.store.utils.StoreLogger

typealias RandomStoreState = RandomState

sealed interface RandomStoreIntent {
    class SetNextPoemBehaviour(val nextPoemBehaviour: RandomPoemBehaviour): RandomStoreIntent
    class SetIsRandomiseSeed(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseIsm(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseThemeMod(val value: Boolean): RandomStoreIntent
}

sealed interface RandomStoreEffect

class RandomStoreReducer(
    val repository: RandomStateRepository
): Reducer<RandomStoreState, RandomStoreIntent, RandomStoreEffect> {
    override suspend fun reduce(
        state: RandomStoreState,
        intent: RandomStoreIntent
    ): ReducerResult<RandomStoreState, out RandomStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is RandomStoreIntent.SetIsRandomiseIsm -> state.copy(isRandomiseIsm = intent.value)
            is RandomStoreIntent.SetIsRandomiseSeed -> state.copy(isRandomiseSeed = intent.value)
            is RandomStoreIntent.SetIsRandomiseThemeMod -> state.copy(isRandomiseThemeMode = intent.value)
            is RandomStoreIntent.SetNextPoemBehaviour -> state.copy(randomPoemBehaviour = intent.nextPoemBehaviour)
        }.also { repository.saveRandomState(it) }
    )
}


class RandomStore(
    repository: RandomStateRepository
): Store<RandomStoreState, RandomStoreIntent, RandomStoreEffect>(
    defaultState = RandomStoreState(),
    initialiseState = { repository.getRandomState() },
    reducer = RandomStoreReducer(repository = repository)
)