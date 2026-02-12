package com.cvetyshayasiren.poetrybook.domain.models.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec

data class StyleState(
    val ismStyle: IsmStyle = IsmStyle.NEU,
    val seedColor: Color = Color(5, 118, 82),
    val themeMode: ThemeMode = ThemeMode.DARK,
    val paletteStyle: PaletteStyle = PaletteStyle.Vibrant,
    val colorSpecVersion: ColorSpec.SpecVersion = ColorSpec.SpecVersion.SPEC_2025
) {
    fun randomised(
        isRandomiseSeed: Boolean,
        isRandomiseIsm: Boolean,
        isRandomiseThemeMode: Boolean,
        isRandomisePaletteStyle: Boolean,
        isRandomiseColorSpecVersion: Boolean,
    ): StyleState = StyleState(
        ismStyle = if(isRandomiseIsm) IsmStyle.random() else ismStyle,
        seedColor = if(isRandomiseSeed) Color.random() else seedColor,
        themeMode = if(isRandomiseThemeMode) ThemeMode.random() else themeMode,
        paletteStyle = if(isRandomisePaletteStyle) PaletteStyle.entries.random() else paletteStyle,
        colorSpecVersion = if(isRandomiseColorSpecVersion) ColorSpec.SpecVersion.entries.random() else colorSpecVersion
    )
    @Composable
    fun isDarkThemeMode(): Boolean = when(themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
}

fun Color.Companion.random(): Color =
    Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )