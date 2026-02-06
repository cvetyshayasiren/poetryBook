package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.contracts.Effect

abstract class Store<S, I, E>(
    defaultState: S,
    initialiseState: (suspend () -> S)? = null,
    sharingStarted: SharingStarted =
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
    private val onResume: ((Store<S, I, E>) -> Unit)? = null,
    private val reducer: Reducer<S, I, E>,
    private val logger: StoreLogger? = StoreLogger.Default()
): ViewModel() {
    private val storeName = this::class.simpleName.toString()

    init { logger?.logStoreInitialised(storeName) }

    protected var stateIsInit: Boolean = false
        private set

    private val intentsChannel = Channel<I>(capacity = Channel.UNLIMITED)
    private val stateMutex = Mutex()

    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> = _state
        .asStateFlow()
        .onStart {
            when(stateIsInit) {
                true -> {
                    logger?.logStoreStateResume(storeName = storeName)
                    onResume?.invoke(this@Store)
                }
                false -> {
                    stateMutex.withLock {
                        initialiseState?.let { _state.emit(it()) }
                        stateIsInit = true
                    }
                    logger?.logStoreStateInitialised(storeName = storeName)
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
        intentsChannel.trySend(intent)
        val intentName = intent?.let { it::class.simpleName } ?: "null"
        logger?.logStoreSendIntent(storeName = storeName, intentName = intentName)
    }

    private suspend fun consumeIntents() {
        intentsChannel.consumeAsFlow().collect { intent ->
            executeIntent(intent)
        }
    }

    private suspend fun executeIntent(intent: I) {
        stateMutex.withLock {
            val (newState, effect, sideEffect) = reducer.reduce(_state.value, intent).value
            _state.emit(newState)
            effect?.let { _effect.emit(it) }
            sideEffect?.invoke()
        }
    }

    protected suspend fun waitUntilInitialized(delay: Long = 1) {
        while (!stateIsInit) { delay(delay) }
    }

    protected suspend fun updateState(
        effect: E? = null,
        sideEffect: (suspend () -> Unit)? = null,
        block: (S) -> S) {
        waitUntilInitialized()
        stateMutex.withLock {
            val newState = block(_state.value)
            _state.emit(newState)
            effect?.let { _effect.emit(it) }
            sideEffect?.invoke()
        }
    }

    protected fun launchAfterInit(block: suspend () -> Unit) {
        viewModelScope.launch {
            waitUntilInitialized()
            block()
        }
    }

    init { launchAfterInit { consumeIntents() } }
}