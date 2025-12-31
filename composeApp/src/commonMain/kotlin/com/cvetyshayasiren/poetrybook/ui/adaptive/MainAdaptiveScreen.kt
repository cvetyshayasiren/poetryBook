package com.cvetyshayasiren.poetrybook.ui.adaptive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.MainNavigationScreen
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.styles.getStyleScreenBundle

@Composable
fun MainAdaptiveScreen() {
    val isExpanded = WindowSizeClass.isExpanded()
    val ismStyle = remember { IsmStyle.NEU }
    val styleScreenBundle = ismStyle.getStyleScreenBundle()
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MainNavigationScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary),
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
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .animateContentSize()
            )
        }
    }
}