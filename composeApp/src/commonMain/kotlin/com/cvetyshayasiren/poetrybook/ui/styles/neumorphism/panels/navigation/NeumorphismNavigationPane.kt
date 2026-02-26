package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Pages
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.isPage
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicIconButton
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.neumorphicDrop
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.neumorphicInner
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugNavigationPane
import org.kodein.di.instance
import kotlin.getValue

@Composable
fun NeumorphismNavigationPane(modifier: Modifier = Modifier, isExpanded: Boolean) {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val navList = Destination.navList(isExpanded)
    val color = MaterialTheme.colorScheme.surfaceBright
    val onColor = MaterialTheme.colorScheme.onSurface

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(NeumorphismConfig.mediumPadding, Alignment.CenterHorizontally),
        modifier = modifier
            .fillMaxWidth()
            .height(NeumorphismConfig.navigationPaneHeight)
            .background(color)
    ) {
        navList.forEach { destination ->
            NeumorphicIconButton(
                color = color,
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(destination))
                }
            ) {
                Icon(
                    imageVector = when(destination) {
                        Destination.Favorites -> Icons.Filled.Favorite
                        Destination.History -> Icons.Filled.History
                        Destination.Page -> Icons.Filled.Pages
                        is Destination.Search -> Icons.Filled.Search
                        Destination.Settings -> Icons.Filled.Settings
                    },
                    tint = onColor,
                    contentDescription = null,
                )
            }
        }

        AnimatedContent(targetState = navigationState.value.isPage()) { isPage ->
            NeumorphicIconButton(
                color = color,
                onClick = {
                    when(isPage) {
                        true -> pageStore.sendIntent(PageStoreIntent.SwitchRandom)
                        false -> navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
                    }
                }
            ) {
                Icon(
                    imageVector = when(isPage) {
                        true -> Icons.Outlined.Casino
                        false -> Icons.Outlined.Pages
                    },
                    tint = onColor,
                    contentDescription = null
                )
            }
        }
    }
}