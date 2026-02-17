package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugFavoritesPane

@Composable
fun NeumorphismFavoritesPane(modifier: Modifier = Modifier) {
    PlugFavoritesPane(
        modifier = modifier,
        style = IsmStyle.NEU
    )
}