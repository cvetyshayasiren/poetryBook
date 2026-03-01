package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugFavoritesPane

@Composable
fun BrutalismFavoritesPane(modifier: Modifier) {
    PlugFavoritesPane(modifier = modifier, style = IsmStyle.BRUT)
}