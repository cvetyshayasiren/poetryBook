package com.cvetyshayasiren.poetrybook.ui.styles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destinations
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugScreenBundle

interface StyleScreenBundle {
    @Composable
    fun NavigationView(
        modifier: Modifier = Modifier,
        backStack: SnapshotStateList<Destinations>,
        isExpanded: Boolean
    )

    @Composable
    fun BookPane(modifier: Modifier = Modifier)

    @Composable
    fun SettingsPane(modifier: Modifier = Modifier)

    @Composable
    fun HistoryPane(modifier: Modifier = Modifier)

    @Composable
    fun FavoritesPane(modifier: Modifier = Modifier)

    @Composable
    fun SearchPane(modifier: Modifier = Modifier)
}

fun IsmStyle.getStyleScreenBundle(): StyleScreenBundle = when(this) {
    IsmStyle.NEU -> PlugScreenBundle(this)
    IsmStyle.BRUT -> PlugScreenBundle(this)
    IsmStyle.BAU -> PlugScreenBundle(this)
    IsmStyle.GLASS -> PlugScreenBundle(this)
}