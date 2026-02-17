package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.panels.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugFavoritesPane

@Composable
fun BauhausFavoritesPane(modifier: Modifier) {
    PlugFavoritesPane(modifier = modifier, style = IsmStyle.BAU)
}