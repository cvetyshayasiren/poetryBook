package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.GlassmorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.components.GlassmorphismIconButton
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.components.glassy
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.components.glassyHazy
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle.NeumorphismAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicIconButton
import org.kodein.di.instance
import kotlin.getValue
import kotlin.math.roundToInt

@Composable
fun GlassmorphismNavigationPane(modifier: Modifier = Modifier)  {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val isExpanded = WindowSizeClass.isExpanded()
    val swapAnimation = remember { NeumorphismAnimationBundle().swapNavigationPane }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GlassmorphismConfig.bigPadding, Alignment.CenterHorizontally),
        modifier = modifier
            .padding(GlassmorphismConfig.bigPadding)
            .glassy()
            .wrapContentSize()
            .padding(horizontal = GlassmorphismConfig.bigPadding, vertical = GlassmorphismConfig.mediumPadding)
    ) {
        GlassmorphismIconButton(
            icon = Icons.Filled.History,
            clamped = navigationState.value.nowIs(Destination.History)
        ) {
            navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.History))
        }

        GlassmorphismIconButton(
            icon = Icons.Filled.Favorite,
            clamped = navigationState.value.nowIs(Destination.Favorites)
        ) {
            navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Favorites))
        }

        AnimatedVisibility(
            visible = !isExpanded,
            enter = swapAnimation.enter,
            exit = swapAnimation.exit,
        ) {
            GlassmorphismIconButton(
                icon = Icons.Filled.Settings,
                clamped = navigationState.value.nowIs(Destination.Settings)
            ) {
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Settings))
            }
        }

        GlassmorphismIconButton(
            icon = Icons.Rounded.Casino
        ) {
            when(navigationState.value.isPage()) {
                true -> pageStore.sendIntent(PageStoreIntent.SwitchRandom)
                false -> navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
            }
        }
    }

}