package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugNavigationPane

@Composable
fun BrutalismNavigationPane(modifier: Modifier = Modifier, isExpanded: Boolean) {
    PlugNavigationPane(modifier = modifier, isExpanded = isExpanded, style = IsmStyle.BRUT)
}