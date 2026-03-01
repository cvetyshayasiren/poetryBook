package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.bundle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.favorites.BauhausFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.history.BauhausHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.navigation.BauhausNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.page.BauhausPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.search.BauhausSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.settings.BauhausSettingsPane

class BauhausScreenBundle: StyleScreenBundle {
    @Composable
    override fun NavigationPane(modifier: Modifier) = BauhausNavigationPane(modifier = modifier)

    @Composable
    override fun PagePane(modifier: Modifier) = BauhausPagePane(modifier = modifier)

    @Composable
    override fun SettingsPane(modifier: Modifier) = BauhausSettingsPane(modifier = modifier)

    @Composable
    override fun HistoryPane(modifier: Modifier) = BauhausHistoryPane(modifier = modifier)

    @Composable
    override fun FavoritesPane(modifier: Modifier) = BauhausFavoritesPane(modifier = modifier)

    @Composable
    override fun SearchPane(modifier: Modifier) = BauhausSearchPane(modifier = modifier)
}