package com.cvetyshayasiren.poetrybook.ui.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugScreenBundle

interface StyleScreenBundle {
    @Composable
    fun NavigationView(
        modifier: Modifier = Modifier,
        isExpanded: Boolean
    )

    @Composable
    fun PagePane(modifier: Modifier = Modifier)

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