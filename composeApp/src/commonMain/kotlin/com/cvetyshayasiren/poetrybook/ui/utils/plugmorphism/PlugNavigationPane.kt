package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Pages
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.current
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.isPage
import com.materialkolor.ktx.darken
import org.kodein.di.instance

@Composable
fun PlugNavigationPane(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    style: IsmStyle,
) {

    data class StyledColors(val color: Color, val onColor: Color)

    @Composable
    fun getStyledColors(ismStyle: IsmStyle): StyledColors = when(ismStyle) {
        IsmStyle.NEU -> StyledColors(color = MaterialTheme.colorScheme.primaryContainer, onColor = MaterialTheme.colorScheme.onPrimaryContainer)
        IsmStyle.BRUT -> StyledColors(color = MaterialTheme.colorScheme.secondaryContainer, onColor = MaterialTheme.colorScheme.onSecondaryContainer)
        IsmStyle.BAU -> StyledColors(color = MaterialTheme.colorScheme.tertiaryContainer, onColor = MaterialTheme.colorScheme.onTertiaryContainer)
        IsmStyle.GLASS -> StyledColors(color = MaterialTheme.colorScheme.errorContainer, onColor = MaterialTheme.colorScheme.onErrorContainer)
    }

    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val navList = Destination.navList(isExpanded)

    val (color, onColor) = getStyledColors(ismStyle = style)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(.8f)
            .clip(RoundedCornerShape(24.dp))
            .background(color)
    ) {
        navList.forEach { destination ->
            val borderColor by animateColorAsState(
                targetValue = if(destination == navigationState.value.current()) onColor else Color.Transparent
            )
            IconButton(
                modifier = Modifier
                    .border(width = 2.dp, color = borderColor, shape = CircleShape),
                shape = CircleShape,
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