package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Pages
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.current
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.navigation.isPage
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle.BrutalismAnimationBundle
import com.materialkolor.ktx.darken
import org.kodein.di.instance

@Composable
fun PlugNavigationPane(modifier: Modifier = Modifier, style: IsmStyle) {

    data class StyledColors(val color: Color, val onColor: Color)

    @Composable
    fun getStyledColors(ismStyle: IsmStyle): StyledColors = when(ismStyle) {
        IsmStyle.NEU -> StyledColors(color = MaterialTheme.colorScheme.primaryContainer, onColor = MaterialTheme.colorScheme.onPrimaryContainer)
        IsmStyle.BRUT -> StyledColors(color = MaterialTheme.colorScheme.secondaryContainer, onColor = MaterialTheme.colorScheme.onSecondaryContainer)
        IsmStyle.BAU -> StyledColors(color = MaterialTheme.colorScheme.tertiaryContainer, onColor = MaterialTheme.colorScheme.onTertiaryContainer)
        IsmStyle.GLASS -> StyledColors(color = MaterialTheme.colorScheme.errorContainer, onColor = MaterialTheme.colorScheme.onErrorContainer)
    }

    val isExpanded = WindowSizeClass.isExpanded()
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()

    val (color, onColor) = getStyledColors(ismStyle = style)
    val swapAnimation = remember { BrutalismAnimationBundle().swapNavigationPane }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(.8f)
            .clip(RoundedCornerShape(24.dp))
            .background(color)
    ) {
        val historyBorderColor by animateColorAsState(
            targetValue = if(navigationState.value.current() == Destination.History) onColor else Color.Transparent
        )
        IconButton(
            modifier = Modifier
                .border(width = 2.dp, color = historyBorderColor, shape = CircleShape),
            shape = CircleShape,
            onClick = {
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.History))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = null,
                tint = onColor
            )
        }

        val favoritesBorderColor by animateColorAsState(
            targetValue = if(navigationState.value.current() == Destination.Favorites) onColor else Color.Transparent
        )
        IconButton(
            modifier = Modifier
                .border(width = 2.dp, color = favoritesBorderColor, shape = CircleShape),
            shape = CircleShape,
            onClick = {
                navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Favorites))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = onColor
            )
        }

        AnimatedVisibility(
            visible = !isExpanded,
            enter = swapAnimation.enter,
            exit = swapAnimation.exit
        ) {
            val settingsBorderColor by animateColorAsState(
                targetValue = if(navigationState.value.current() == Destination.Settings) onColor else Color.Transparent
            )
            IconButton(
                modifier = Modifier
                    .border(width = 2.dp, color = settingsBorderColor, shape = CircleShape),
                shape = CircleShape,
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Settings))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = onColor
                )
            }
        }

        val isPage = navigationState.value.isPage()
        val borderColor by animateColorAsState(
            targetValue = if(isPage) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent
        )
        val diceColor by animateColorAsState(if(isPage) onColor else onColor.darken(1.5f))
        IconButton(
            modifier = Modifier
                .border(width = 2.dp, color = borderColor, shape = CircleShape),
            colors = IconButtonDefaults.iconButtonColors(containerColor = diceColor),
            shape = CircleShape,
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
                contentDescription = null,
                tint = color
            )
        }
    }
}