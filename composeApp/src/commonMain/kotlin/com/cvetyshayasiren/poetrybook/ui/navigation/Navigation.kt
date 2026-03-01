package com.cvetyshayasiren.poetrybook.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import org.kodein.di.instance

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
    styleScreenBundle: StyleScreenBundle,
    animationBundle: AnimationBundle,
    isExpanded: Boolean
) {
    val navigationStore: NavigationStore by di.instance()
    val state = navigationStore.state.collectAsState()

    LaunchedEffect(isExpanded) {
        if(isExpanded && (state.value.current() is Destination.Settings)) {
            navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(Destination.Page))
        }
    }

    Box(modifier = modifier) {
        NavDisplay(
            transitionSpec = { animationBundle.navigate.contentTransform },
            modifier = Modifier.fillMaxSize(),
            backStack = state.value,
            onBack = { navigationStore.sendIntent(NavigationStoreIntent.Back) },
            entryProvider = { key ->
                when(key) {
                    Destination.Page -> NavEntry(key) { styleScreenBundle.PagePane() }
                    Destination.Favorites -> NavEntry(key) { styleScreenBundle.FavoritesPane() }
                    Destination.History -> NavEntry(key) { styleScreenBundle.HistoryPane() }
                    Destination.Search -> NavEntry(key) { styleScreenBundle.SearchPane() }
                    Destination.Settings -> NavEntry(key) { styleScreenBundle.SettingsPane() }
                }
            }
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            styleScreenBundle.NavigationPane()
        }
    }
}