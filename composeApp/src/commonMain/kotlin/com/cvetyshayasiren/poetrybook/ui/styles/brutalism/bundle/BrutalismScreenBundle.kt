package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.favorites.BrutalismFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.history.BrutalismHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.navigation.BrutalismNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.page.BrutalismPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.search.BrutalismSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.settings.BrutalismSettingsPane

class BrutalismScreenBundle: StyleScreenBundle {
    @Composable
    override fun NavigationPane(modifier: Modifier) = BrutalismNavigationPane(modifier = modifier)

    @Composable
    override fun PagePane(modifier: Modifier) = BrutalismPagePane(modifier = modifier)

    @Composable
    override fun SettingsPane(modifier: Modifier) = BrutalismSettingsPane(modifier = modifier)

    @Composable
    override fun HistoryPane(modifier: Modifier) = BrutalismHistoryPane(modifier = modifier)

    @Composable
    override fun FavoritesPane(modifier: Modifier) = BrutalismFavoritesPane(modifier = modifier)

    @Composable
    override fun SearchPane(modifier: Modifier) = BrutalismSearchPane(modifier = modifier)
}