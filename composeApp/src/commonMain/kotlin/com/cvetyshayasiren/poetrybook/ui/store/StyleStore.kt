package com.cvetyshayasiren.poetrybook.ui.store

import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.StyleState
import com.cvetyshayasiren.poetrybook.domain.ThemeMode
import com.cvetyshayasiren.poetrybook.domain.repository.StyleStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StyleStoreState(
    val style: StyleState
)

sealed interface StyleStoreIntent {
    class SetIsmStyle(val ismStyle: IsmStyle): StyleStoreIntent
    class SetSeedColor(val seedColor: Color): StyleStoreIntent
    class SetThemeMode(val themeMode: ThemeMode): StyleStoreIntent
}

class StyleStoreReducer: Reducer<StyleStoreState, StyleStoreIntent> {

    override fun reduce(state: StyleStoreState, intent: StyleStoreIntent): StyleStoreState {
        return when(intent) {
            is StyleStoreIntent.SetIsmStyle -> StyleStoreState(state.style.copy(ismStyle = intent.ismStyle))
            is StyleStoreIntent.SetSeedColor -> StyleStoreState(state.style.copy(seedColor = intent.seedColor))
            is StyleStoreIntent.SetThemeMode -> StyleStoreState(state.style.copy(themeMode = intent.themeMode))
        }
    }

}

class StyleStore(
    val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    val repository: StyleStateRepository,
    private val reducer: StyleStoreReducer
): Store<StyleStoreIntent> {
    private val _state = MutableStateFlow(StyleStoreState(repository.getStyleState()))
    val state: StateFlow<StyleStoreState> = _state.asStateFlow()

    override fun sendIntent(intent: StyleStoreIntent) {
        scope.launch {
            val newState = reducer.reduce(_state.value, intent)
            repository.saveStyleState(newState.style)
            _state.emit(newState)
        }
    }
}