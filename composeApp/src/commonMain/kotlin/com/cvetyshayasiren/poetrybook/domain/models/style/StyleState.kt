package com.cvetyshayasiren.poetrybook.domain.models.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class StyleState(
    val ismStyle: IsmStyle = IsmStyle.NEU,
    val seedColor: Color = Color.Unspecified,
    val themeMode: ThemeMode = ThemeMode.DARK
) {
    @Composable
    fun isDarkThemeMode(): Boolean = when(themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
}