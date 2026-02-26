package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.isPage
import com.cvetyshayasiren.poetrybook.ui.store.nowIs
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.BrutalismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.components.BrutalismIconButton
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicIconButton
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugNavigationPane
import org.kodein.di.instance
import kotlin.getValue

@Composable
fun BrutalismNavigationPane(modifier: Modifier = Modifier, isExpanded: Boolean) {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val navList = Destination.navList(isExpanded)
    val color = MaterialTheme.colorScheme.primary
    val onColor = MaterialTheme.colorScheme.onPrimary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(NeumorphismConfig.mediumPadding, Alignment.CenterHorizontally),
        modifier = modifier
            .fillMaxWidth(.8f)
            .wrapContentHeight()
            .padding(BrutalismConfig.bigPadding)
    ) {
        navList.forEach { destination ->
            BrutalismIconButton(
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(destination))
                }
            ) {
                Icon(
                    imageVector = when(destination) {
                        Destination.Favorites -> Icons.Rounded.Favorite
                        Destination.History -> Icons.Rounded.History
                        Destination.Page -> Icons.Rounded.Replay
                        is Destination.Search -> Icons.Rounded.Search
                        Destination.Settings -> Icons.Rounded.Settings
                    },
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