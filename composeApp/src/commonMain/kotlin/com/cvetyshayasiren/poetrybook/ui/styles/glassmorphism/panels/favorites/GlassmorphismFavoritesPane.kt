package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugFavoritesPane

@Composable
fun GlassmorphismFavoritesPane(modifier: Modifier = Modifier) {
    PlugFavoritesPane(
        modifier = modifier,
        style = IsmStyle.GLASS
    )
}