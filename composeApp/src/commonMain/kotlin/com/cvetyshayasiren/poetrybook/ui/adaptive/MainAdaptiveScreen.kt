package com.cvetyshayasiren.poetrybook.ui.adaptive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.navigation.MainNavigationScreen
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.styles.getStyleScreenBundle
import org.kodein.di.instance

@Composable
fun MainAdaptiveScreen() {
    val styleStore: StyleStore by di.instance()
    val styleState = styleStore.state.collectAsState()
    val isExpanded = WindowSizeClass.isExpanded()
    val styleScreenBundle = styleState.value.ismStyle.getStyleScreenBundle()
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MainNavigationScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            styleScreenBundle = styleScreenBundle,
            isExpanded = isExpanded
        )

        AnimatedVisibility(
            visible = isExpanded
        ) {
            styleScreenBundle.SettingsPane(
                modifier = Modifier
                    .width(360.dp)
                    .fillMaxHeight()
                    .animateContentSize()
            )
        }
    }
}