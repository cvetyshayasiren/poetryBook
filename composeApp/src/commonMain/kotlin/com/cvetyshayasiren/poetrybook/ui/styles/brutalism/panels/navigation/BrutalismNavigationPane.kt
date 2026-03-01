package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onLayoutRectChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.currentMenuIndex
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.navigation.isPage
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.BrutalismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle.BrutalismAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.components.BrutalismIconButton
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import kotlinx.coroutines.launch
import org.kodein.di.instance

@Composable
fun BrutalismNavigationPane(modifier: Modifier = Modifier) {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val isExpanded = WindowSizeClass.isExpanded()
    val color = MaterialTheme.colorScheme.primary
    val onColor = MaterialTheme.colorScheme.onPrimary
    val padding = remember { NeumorphismConfig.bigPadding }
    val animationSpec = remember { BrutalismAnimationBundle().getAnimationSpec<Float>() }
    val swapAnimation = remember { BrutalismAnimationBundle().swapNavigationPane }
    val density = LocalDensity.current
    val width = remember { mutableStateOf(0.dp) }
    val currentMenuIndex = navigationState.value.currentMenuIndex()
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    SideEffect() {
        scope.launch {
            currentMenuIndex?.let { index ->
                offset.animateTo(
                    targetValue = (index * width.value.value) + padding.value / 2,
                    animationSpec = animationSpec
                )
            }
        }
    }

    Box(
        contentAlignment = Alignment.CenterStart
    ) {

        Box(modifier = Modifier
            .offset(x = offset.value.dp)
            .rotate(offset.value)
            .scale(.8f)
            .size(width.value)
            .clip(BrutalismConfig.defaultRoundedShape)
            .background(MaterialTheme.colorScheme.error)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding, Alignment.CenterHorizontally),
            modifier = modifier
                .wrapContentSize()
                .padding(padding)
        ) {
            BrutalismIconButton(
                modifier = Modifier
                    .onLayoutRectChanged { layout ->
                        width.value = with(density) { layout.width.toDp() + padding }
                    },
                buttonColor = color,
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.History))
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.History,
                    tint = onColor,
                    contentDescription = null,
                )
            }

            BrutalismIconButton(
                buttonColor = color,
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Favorites))
                }
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
                exit = swapAnimation.exit
            ) {
                BrutalismIconButton(
                    buttonColor = color,
                    onClick = {
                        navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Settings))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        tint = onColor,
                        contentDescription = null,
                    )
                }
            }

            BrutalismIconButton(
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
}