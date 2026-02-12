package com.cvetyshayasiren.poetrybook.ui.theme

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.rememberDynamicMaterialThemeState
import org.kodein.di.instance

@Composable
fun PoetryBookTheme(
    content: @Composable () -> Unit
) {
    val store: StyleStore by di.instance()
    val state = store.state.collectAsState()

    val dynamicThemeState = rememberDynamicMaterialThemeState(
        isDark = state.value.isDarkThemeMode(),
        style = state.value.paletteStyle,
        specVersion = state.value.colorSpecVersion,
        seedColor = state.value.seedColor
    )

    DynamicMaterialTheme(
        state = dynamicThemeState,
        animate = true,
        animationSpec = tween(durationMillis = 1000),
        content = content,
    )
}