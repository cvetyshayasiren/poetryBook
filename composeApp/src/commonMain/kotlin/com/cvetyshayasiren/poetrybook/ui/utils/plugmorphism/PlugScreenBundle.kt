package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle

class PlugScreenBundle(val style: IsmStyle): StyleScreenBundle {
    @Composable
    override fun NavigationView(
        modifier: Modifier,
        isExpanded: Boolean
    ) = PlugNavigationView(modifier, isExpanded, style)

    @Composable
    override fun BookPane(modifier: Modifier) = PlugBookPane(modifier, style)

    @Composable
    override fun SettingsPane(modifier: Modifier) = PlugSettingsPane(modifier, style)

    @Composable
    override fun HistoryPane(modifier: Modifier) = PlugHistoryPane(modifier, style)

    @Composable
    override fun FavoritesPane(modifier: Modifier) = PlugFavoritesPane(modifier, style)

    @Composable
    override fun SearchPane(modifier: Modifier) = PlugSearchPane(modifier, style)
}