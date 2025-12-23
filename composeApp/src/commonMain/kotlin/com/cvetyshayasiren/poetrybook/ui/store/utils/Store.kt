package com.cvetyshayasiren.poetrybook.ui.store.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.ui.store.StyleStoreState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

abstract class Store<S, Intent>(
    initialiseState: () -> S,
    private val reducer: Reducer<S, Intent>
): ViewModel() {
    private val _state = MutableStateFlow<S>(initialiseState())
    val state: StateFlow<S> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000),
        initialValue = initialiseState()
    )

    fun sendIntent(
        intent: Intent,
        sideEffect: ((state: S) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val newState = reducer.reduce(_state.value, intent)
            _state.emit(newState)
            sideEffect?.run { this@run.invoke(newState) }
        }
    }
}