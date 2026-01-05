package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
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
import com.cvetyshayasiren.poetrybook.ui.store.PoetryBookStore
import com.materialkolor.ktx.darken
import kotlinx.coroutines.launch
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
    val buttonSize = remember { DpSize(150.dp, 50.dp) }
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
            Button(
                modifier = Modifier.size(buttonSize),
                border = BorderStroke(width = 2.dp, color = borderColor),
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(destination))
                }
            ) {
                Text(destination::class.simpleName ?: "???")
            }
        }
        val isPage = navigationState.value.current() == Destination.Page
        val borderColor by animateColorAsState(
            targetValue = if(isPage) MaterialTheme.colorScheme.tertiary else Color.Transparent
        )
        val diceSize by animateFloatAsState(if(isPage) 1f else .5f)
        val diceColor by animateColorAsState(
            if(isPage) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.darken(1.5f)
        )
        Button(
            modifier = Modifier
                .size(buttonSize * diceSize),
            border = BorderStroke(width = 2.dp, color = borderColor),
            colors = ButtonDefaults.buttonColors(containerColor = diceColor),
            onClick = {
                when(isPage) {
                    true -> pageStore.sendIntent(PageStoreIntent.SwitchRandom)
                    false -> navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
                }
            }
        ) {
            Text(if(isPage) "dice" else "...")
        }
    }
}