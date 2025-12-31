package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.Destinations
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store

data class NavigationStoreState(
    val backstack: Destinations = listOf(Destination.Page)
)

sealed interface NavigationStoreIntent {
    data object Back: NavigationStoreIntent
    data class NavigateTo(val destination: Destination): NavigationStoreIntent
}

sealed interface NavigationStoreEffect

class NavigationStoreReducer:
    Reducer<NavigationStoreState, NavigationStoreIntent, NavigationStoreEffect> {
    override suspend fun reduce(
        state: NavigationStoreState,
        intent: NavigationStoreIntent
    ): ReducerResult<NavigationStoreState, out NavigationStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is NavigationStoreIntent.Back ->
                state.copy(backstack = state.backstack.dropLast(1))
            is NavigationStoreIntent.NavigateTo ->
                state.copy(backstack = state.backstack.plus(intent.destination))
        }
    )
}

class NavigationStore:
    Store<NavigationStoreState, NavigationStoreIntent, NavigationStoreEffect>(
        defaultState = NavigationStoreState(),
        reducer = NavigationStoreReducer(),
        tag = "NavigationStore"
    )