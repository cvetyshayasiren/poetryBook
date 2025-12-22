package com.cvetyshayasiren.poetrybook.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
    styleScreenBundle: StyleScreenBundle,
    isExpanded: Boolean
) {
    val backStack = remember { mutableStateListOf<Destinations>(Destinations.Book) }

    Box(modifier = modifier) {
        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider =  { key ->
                when(key) {
                    Destinations.Book -> NavEntry(key) { styleScreenBundle.BookPane() }
                    Destinations.Favorites -> NavEntry(key) { styleScreenBundle.FavoritesPane() }
                    Destinations.History -> NavEntry(key) { styleScreenBundle.HistoryPane() }
                    Destinations.Search -> NavEntry(key) { styleScreenBundle.SearchPane() }
                    Destinations.Settings -> NavEntry(key) { styleScreenBundle.SettingsPane() }
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
            backStack = backStack,
            isExpanded = isExpanded
        )
    }
}