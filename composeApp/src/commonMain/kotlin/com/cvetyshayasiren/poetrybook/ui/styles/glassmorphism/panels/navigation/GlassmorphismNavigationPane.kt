package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugNavigationPane

@Composable
fun GlassmorphismNavigationPane(modifier: Modifier = Modifier, isExpanded: Boolean)  {
    PlugNavigationPane(modifier = modifier, style = IsmStyle.GLASS, isExpanded = isExpanded)
}