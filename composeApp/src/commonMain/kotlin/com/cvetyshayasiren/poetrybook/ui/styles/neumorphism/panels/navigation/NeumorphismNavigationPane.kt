package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.navigation.isPage
import com.cvetyshayasiren.poetrybook.ui.navigation.nowIs
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle.NeumorphismAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicIconButton
import org.kodein.di.instance

@Composable
fun NeumorphismNavigationPane(modifier: Modifier = Modifier) {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val isExpanded = WindowSizeClass.isExpanded()
    val color = MaterialTheme.colorScheme.surfaceBright
    val onColor = MaterialTheme.colorScheme.onSurface
    val swapAnimation = remember { NeumorphismAnimationBundle().swapNavigationPane }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(NeumorphismConfig.mediumPadding, Alignment.CenterHorizontally),
        modifier = modifier
            .fillMaxWidth()
            .height(NeumorphismConfig.navigationPaneHeight)
            .background(color)
    ) {
        NeumorphicIconButton(
            color = color,
            onClick = {
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.History))
            },
            clamped = navigationState.value.nowIs(Destination.History),
        ) {
            Icon(
                imageVector = Icons.Rounded.History,
                tint = onColor,
                contentDescription = null,
            )
        }

        NeumorphicIconButton(
            color = color,
            onClick = {
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Favorites))
            },
            clamped = navigationState.value.nowIs(Destination.Favorites),
        ) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                tint = onColor,
                contentDescription = null,
            )
        }

        AnimatedVisibility(
            visible = !isExpanded,
            enter = swapAnimation.enter,
            exit = swapAnimation.exit,
        ) {
            NeumorphicIconButton(
                color = color,
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Settings))
                },
                clamped = navigationState.value.nowIs(Destination.Settings),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    tint = onColor,
                    contentDescription = null,
                )
            }
        }

        NeumorphicIconButton(
            color = color,
            onClick = {
                when(navigationState.value.isPage()) {
                    true -> pageStore.sendIntent(PageStoreIntent.SwitchRandom)
                    false -> navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
                }
            }
        ) {
            AnimatedContent(
                targetState = navigationState.value.isPage()
            ) { isPage ->
                Icon(
                    imageVector = when(isPage) {
                        true -> Icons.Rounded.Casino
                        false -> Icons.Rounded.Replay
                    },
                    tint = onColor,
                    contentDescription = null
                )
            }
        }
    }
}