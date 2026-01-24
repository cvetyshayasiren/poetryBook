package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.navigation.current
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.materialkolor.ktx.darken
import org.kodein.di.instance

@Composable
fun PlugNavigationView(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    style: IsmStyle,
) {
    val navigationStore: NavigationStore by di.instance()
    val navigationState = navigationStore.state.collectAsState()
    val pageStore: PageStore by di.instance()
    val navList = Destination.navList(isExpanded)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(.8f)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        navList.forEach { destination ->
            val borderColor by animateColorAsState(
                targetValue = if(destination == navigationState.value.current())
                    MaterialTheme.colorScheme.tertiary else Color.Transparent
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
                        Destination.Search -> Icons.Filled.Search
                        Destination.Settings -> Icons.Filled.Settings
                    },
                    contentDescription = null,
                )
            }
        }
        val isPage = navigationState.value.current() == Destination.Page
        val borderColor by animateColorAsState(
            targetValue = if(isPage) MaterialTheme.colorScheme.tertiary else Color.Transparent
        )
        val diceColor by animateColorAsState(
            if(isPage) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.darken(1.5f)
        )
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
                contentDescription = null
            )
        }
    }
}