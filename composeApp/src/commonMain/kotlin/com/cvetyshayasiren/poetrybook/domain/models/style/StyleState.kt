package com.cvetyshayasiren.poetrybook.domain.models.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class StyleState(
    val ismStyle: IsmStyle = IsmStyle.NEU,
    val seedColor: Color = Color.Unspecified,
    val themeMode: ThemeMode = ThemeMode.DARK
) {
    fun randomised(
        isRandomiseSeed: Boolean,
        isRandomiseIsm: Boolean,
        isRandomiseThemeMode: Boolean
    ): StyleState = StyleState(
        ismStyle = if(isRandomiseIsm) IsmStyle.random() else ismStyle,
        seedColor = if(isRandomiseSeed) Color.random() else seedColor,
        themeMode = if(isRandomiseThemeMode) ThemeMode.random() else themeMode
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