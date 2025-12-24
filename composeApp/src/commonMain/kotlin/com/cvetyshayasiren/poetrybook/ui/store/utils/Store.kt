package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.ui.store.StyleStoreState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

abstract class Store<S, I, E>(
    defaultState: S,
    initialiseState: suspend () -> S,
    private val reducer: Reducer<S, I, E>
): ViewModel() {
    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> = _state
        .asStateFlow()
        .onStart { emit(initialiseState()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
            initialValue = defaultState
        )

    private val _effect: MutableSharedFlow<E> = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect
        .asSharedFlow()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
        )

    fun sendIntent(
        intent: I
    ) {
        viewModelScope.launch {
            val (newState, effect) = reducer.reduce(_state.value, intent).value
            _state.emit(newState)
            effect?.let { _effect.emit(it) }
        }
    }
}