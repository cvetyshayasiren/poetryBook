package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class Store<S, I, E>(
    defaultState: S,
    initialiseState: (suspend () -> S)? = null,
    sharingStarted: SharingStarted =
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
    private val reducer: Reducer<S, I, E>,
    private val logger: StoreLogger? = StoreLogger.Default()
): ViewModel() {
    private val storeName = this::class.simpleName.toString()

    init { logger?.logStoreInitialised(storeName) }

    private var stateIsInit: Boolean = false
    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> = _state
        .asStateFlow()
        .onStart {
            when(stateIsInit) {
                true -> {
                    logger?.logStoreStateStart(storeName = storeName)
                }
                false -> {
                    initialiseState?.let { _state.emit(it()) }
                    logger?.logStoreStateInitialised(storeName = storeName)
                    stateIsInit = true
                }
            }
        }
        .onCompletion { logger?.logStoreStateCompletion(storeName = storeName, throwable = it) }
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = defaultState
        )

    private val _effect: MutableSharedFlow<E> = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect
        .asSharedFlow()
        .onStart { logger?.logStoreEffectStart(storeName = storeName) }
        .onCompletion { logger?.logStoreEffectCompletion(storeName = storeName, throwable = it) }
        .shareIn(
            scope = viewModelScope,
            started = sharingStarted,
        )

    fun sendIntent(intent: I) {
        val intentName = intent?.let { it::class.simpleName } ?: "null"
        viewModelScope.launch {
            val (newState, effect) = reducer.reduce(_state.value, intent).value
            _state.emit(newState)
            effect?.let { _effect.emit(it) }
            logger?.logStoreSendIntent(storeName = storeName, intentName = intentName)
        }
    }
}