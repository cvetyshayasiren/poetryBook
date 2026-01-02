package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class Store<S, I, E>(
    defaultState: S,
    initialiseState: (suspend () -> S)? = null,
    private val reducer: Reducer<S, I, E>,
    tag: String? = null
): ViewModel() {
    private val tag = tag ?: ""
    private val storeName = this::class.simpleName
    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> = _state
        .asStateFlow()
        .onStart {
            initialiseState?.let { _state.emit(it()) }
            Logger.i(this@Store.tag) { "[$storeName] initialise state" }
        }
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
        val intentName = if(intent == null) "null" else intent::class.simpleName
        viewModelScope.launch {
            val (newState, effect) = reducer.reduce(_state.value, intent).value
            _state.emit(newState)
            effect?.let { _effect.emit(it) }
            Logger.i(tag) { "send intent [$intentName]" }
        }
    }
}