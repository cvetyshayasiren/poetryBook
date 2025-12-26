package com.cvetyshayasiren.poetrybook.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.StyleState
import com.cvetyshayasiren.poetrybook.domain.ThemeMode
import kotlinx.serialization.Serializable

@Serializable
data class DataStyleState(
    val ismStyle: DataIsmStyle,
    val seedColor: Int,
    val themeMode: DataThemeMode
) {
    fun toStyleState(): StyleState = StyleState(
        ismStyle = this.ismStyle.toIsmStyle(),
        seedColor = Color(this.seedColor),
        themeMode = this.themeMode.toThemeMode()
    )
    companion object {
        fun fromStyleState(state: StyleState): DataStyleState = DataStyleState(
            ismStyle = DataIsmStyle.fromIsmStyle(state.ismStyle),
            seedColor = state.seedColor.toArgb(),
            themeMode = DataThemeMode.fromThemeMode(state.themeMode)
        )
    }
}

enum class DataIsmStyle {
    NEU, BRUT, BAU, GLASS;

    fun toIsmStyle(): IsmStyle = when(this) {
        NEU -> IsmStyle.NEU
        BRUT -> IsmStyle.BRUT
        BAU -> IsmStyle.BAU
        GLASS -> IsmStyle.GLASS
    }

    companion object {
        fun fromIsmStyle(style: IsmStyle): DataIsmStyle = when(style) {
            IsmStyle.NEU -> NEU
            IsmStyle.BRUT -> BRUT
            IsmStyle.BAU -> BAU
            IsmStyle.GLASS -> GLASS
        }
    }
}

enum class DataThemeMode {
    DARK, LIGHT, SYSTEM;

    fun toThemeMode(): ThemeMode = when(this) {
        DARK -> ThemeMode.DARK
        LIGHT -> ThemeMode.LIGHT
        SYSTEM -> ThemeMode.SYSTEM
    }

    companion object {
        fun fromThemeMode(mode: ThemeMode): DataThemeMode = when(mode) {
            ThemeMode.DARK -> DARK
            ThemeMode.LIGHT -> LIGHT
            ThemeMode.SYSTEM -> SYSTEM
        }
    }
}
