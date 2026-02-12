package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class Store<S, I, E>(
    defaultState: S,
    sharingStarted: SharingStarted =
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
    private val reducer: Reducer<S, I, E>,
    private val initialiseState: (suspend () -> S)? = null,
    private val onStateStart: ((Store<S, I, E>) -> Unit)? = null,
    private val onStateResume: ((Store<S, I, E>) -> Unit)? = null,
    private val onEffectStart: ((Store<S, I, E>) -> Unit)? = null,
    private val onEffectResume: ((Store<S, I, E>) -> Unit)? = null,
    private val logger: StoreLogger? = StoreLogger.Default()
): ViewModel() {
    private val storeName = this::class.simpleName.toString()
    init { logger?.logStoreInitialised(storeName) }

    protected var stateIsInit: Boolean = false
        private set
    protected var stateIsStarted: Boolean = false
        private set

    private val intentsChannel = Channel<I>(capacity = Channel.UNLIMITED)
    private val stateMutex = Mutex()

    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> = _state
        .asStateFlow()
        .onStart {
            when(stateIsStarted) {
                true -> {
                    logger?.logStoreStateResume(storeName = storeName)
                    onStateResume?.invoke(this@Store)
                }
                false -> {
                    waitUntilInitialized()
                    stateIsStarted = true
                    logger?.logStoreStateStart(storeName = storeName)
                    onStateStart?.invoke(this@Store)
                }
            }
        }
        .onCompletion { logger?.logStoreStateCompletion(storeName = storeName, throwable = it) }
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = defaultState
        )

    init {
        CoroutineScope(Dispatchers.Default).launch {
            stateMutex.withLock {
                if(!stateIsInit) {
                    initialiseState?.let { _state.emit(it()) }
                    stateIsInit = true
                }
            }
            logger?.logStoreStateInitialised(storeName = storeName)
        }
    }

    protected var effectIsStarted: Boolean = false
        private set
    private val _effect: MutableSharedFlow<E> = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect
        .asSharedFlow()
        .onStart {
            when(effectIsStarted) {
                true -> {
                    logger?.logStoreEffectResume(storeName = storeName)
                    onEffectResume?.invoke(this@Store)
                }
                false -> {
                    effectIsStarted = true
                    logger?.logStoreEffectStart(storeName = storeName)
                    onEffectStart?.invoke(this@Store)
                }
            }
        }
        .onCompletion { logger?.logStoreEffectCompletion(storeName = storeName, throwable = it) }
        .shareIn(
            scope = viewModelScope,
            started = sharingStarted,
        )

    fun sendIntent(intent: I) {
        intentsChannel.trySend(intent)
        logger?.logStoreSendIntent(storeName = storeName, intentName = getIntentName(intent))
    }

    private suspend fun consumeIntents() {
        intentsChannel.consumeAsFlow().collect { intent ->
            logger?.logStoreConsumeIntent(storeName = storeName, intentName = getIntentName(intent))
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
        logger?.logStoreExecuteIntent(storeName = storeName, intentName = getIntentName(intent))
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

    private fun getIntentName(intent: I): String = intent?.let { it::class.simpleName } ?: "null"

    init { launchAfterInit { consumeIntents() } }
}