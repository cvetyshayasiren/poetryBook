package com.cvetyshayasiren.poetrybook.ui.store

import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.models.style.StyleState
import com.cvetyshayasiren.poetrybook.domain.models.style.ThemeMode
import com.cvetyshayasiren.poetrybook.domain.repository.StyleStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec

typealias StyleStoreState = StyleState
sealed interface StyleStoreIntent {
    class SetIsmStyle(val ismStyle: IsmStyle): StyleStoreIntent
    class SetSeedColor(val seedColor: Color): StyleStoreIntent
    class SetThemeMode(val themeMode: ThemeMode): StyleStoreIntent
    class SetPaletteStyle(val paletteStyle: PaletteStyle): StyleStoreIntent
    class SetColorSpecVersion(val colorSpecVersion: ColorSpec.SpecVersion): StyleStoreIntent
    data object SetDefault: StyleStoreIntent

    class RandomiseStyle(
        val isRandomiseSeed: Boolean = false,
        val isRandomiseIsm: Boolean = false,
        val isRandomiseThemeMode: Boolean = false,
        val isRandomisePaletteStyle: Boolean = false,
        val isRandomiseColorSpecVersion: Boolean = false
    ): StyleStoreIntent
}

sealed interface StyleStoreEffect

class StyleStoreReducer(): Reducer<StyleStoreState, StyleStoreIntent, StyleStoreEffect> {

    override suspend fun reduce(state: StyleStoreState, intent: StyleStoreIntent):
            ReducerResult<StyleStoreState, out StyleStoreEffect?> = ReducerResult.build {
        newState = when(intent) {
            is StyleStoreIntent.SetIsmStyle -> state.copy(ismStyle = intent.ismStyle)
            is StyleStoreIntent.SetSeedColor -> state.copy(seedColor = intent.seedColor)
            is StyleStoreIntent.SetThemeMode -> state.copy(themeMode = intent.themeMode)
            is StyleStoreIntent.SetPaletteStyle -> state.copy(paletteStyle = intent.paletteStyle)
            is StyleStoreIntent.SetColorSpecVersion -> state.copy(colorSpecVersion = intent.colorSpecVersion)
            is StyleStoreIntent.SetDefault-> StyleState()
            is StyleStoreIntent.RandomiseStyle -> {
                state.randomised(
                    isRandomiseIsm = intent.isRandomiseIsm,
                    isRandomiseSeed = intent.isRandomiseSeed,
                    isRandomiseThemeMode = intent.isRandomiseThemeMode,
                    isRandomisePaletteStyle = intent.isRandomisePaletteStyle,
                    isRandomiseColorSpecVersion = intent.isRandomiseColorSpecVersion
                )
            }
        }
    }
}

class StyleStore(
    repository: StyleStateRepository
): Store<StyleStoreState, StyleStoreIntent, StyleStoreEffect>(
    defaultState = StyleStoreState(),
    initialiseState = { repository.getStyleState() },
    reducer = StyleStoreReducer()
) {
    init {
        launchAfterInit { savingDaemon(repository) }
    }

    suspend fun savingDaemon(repository: StyleStateRepository) {
        state.collect { styleStoreState ->
            if(stateIsInit) { repository.saveStyleState(styleStoreState) }
        }
    }
}