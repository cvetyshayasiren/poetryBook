package com.cvetyshayasiren.poetrybook.ui.store

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.data.repository.StyleStateRepositoryImplementation
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.StyleState
import com.cvetyshayasiren.poetrybook.domain.ThemeMode
import com.cvetyshayasiren.poetrybook.domain.repository.StyleStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

typealias StyleStoreState = StyleState
sealed interface StyleStoreIntent {
    class SetIsmStyle(val ismStyle: IsmStyle): StyleStoreIntent
    class SetSeedColor(val seedColor: Color): StyleStoreIntent
    class SetThemeMode(val themeMode: ThemeMode): StyleStoreIntent
}

sealed interface StyleStoreEffect

class StyleStoreReducer(
    val repository: StyleStateRepository
): Reducer<StyleStoreState, StyleStoreIntent, StyleStoreEffect> {

    override suspend fun reduce(state: StyleStoreState, intent: StyleStoreIntent):
            ReducerResult<StyleStoreState, out StyleStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is StyleStoreIntent.SetIsmStyle -> state.copy(ismStyle = intent.ismStyle)
            is StyleStoreIntent.SetSeedColor -> state.copy(seedColor = intent.seedColor)
            is StyleStoreIntent.SetThemeMode -> state.copy(themeMode = intent.themeMode)
        }.also { repository.saveStyleState(it) }
    )
}

class StyleStore(
    repository: StyleStateRepository
): Store<StyleStoreState, StyleStoreIntent, StyleStoreEffect>(
    defaultState = StyleStoreState(),
    initialiseState = { repository.getStyleState() },
    reducer = StyleStoreReducer(repository = repository),
    tag = "StyleStore"
)