package com.cvetyshayasiren.poetrybook.ui.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.styles.animationBundle
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.rememberDynamicMaterialThemeState
import org.kodein.di.instance

@Composable
fun PoetryBookTheme(
    content: @Composable () -> Unit
) {
    val store: StyleStore by di.instance()
    val state = store.state.collectAsState()
    val animationBundle = state.value.ismStyle.animationBundle

    val dynamicThemeState = rememberDynamicMaterialThemeState(
        isDark = state.value.isDarkThemeMode(),
        style = state.value.paletteStyle,
        specVersion = state.value.colorSpecVersion,
        seedColor = state.value.seedColor
    )

    DynamicMaterialTheme(
        state = dynamicThemeState,
        animate = true,
        animationSpec = animationBundle.colorAnimationSpec
    ) {
        Surface {
            AnimatedContent(
                targetState = state.value,
                transitionSpec = { animationBundle.appearance.contentTransform },
                contentKey = { it.ismStyle },
                content = { content() }
            )
        }
    }
}