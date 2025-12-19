package com.cvetyshayasiren.poetrybook.domain

import androidx.compose.ui.graphics.Color

data class StyleState(
    val ismStyle: IsmStyle = IsmStyle.NEU,
    val seedColor: Color = Color.Red,
    val themeMode: ThemeMode = ThemeMode.DARK
)