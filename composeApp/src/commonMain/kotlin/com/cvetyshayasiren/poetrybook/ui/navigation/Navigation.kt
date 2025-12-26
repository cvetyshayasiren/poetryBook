package com.cvetyshayasiren.poetrybook.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import org.kodein.di.instance
import org.kodein.di.newInstance

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
    styleScreenBundle: StyleScreenBundle,
    isExpanded: Boolean
) {
    val navigationStore: NavigationStore by di.instance()
    val state = navigationStore.state.collectAsState()

    Box(modifier = modifier) {
        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            backStack = state.value.backstack,
            onBack = { navigationStore.sendIntent(NavigationStoreIntent.Back) },
            entryProvider =  { key ->
                when(key) {
                    Destination.Book -> NavEntry(key) { styleScreenBundle.BookPane() }
                    Destination.Favorites -> NavEntry(key) { styleScreenBundle.FavoritesPane() }
                    Destination.History -> NavEntry(key) { styleScreenBundle.HistoryPane() }
                    Destination.Search -> NavEntry(key) { styleScreenBundle.SearchPane() }
                    Destination.Settings -> NavEntry(key) { styleScreenBundle.SettingsPane() }
                }
            }
        )
        styleScreenBundle.NavigationView(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(.8f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiaryFixedDim)
                .align(Alignment.BottomCenter),
            isExpanded = isExpanded
        )
    }
}