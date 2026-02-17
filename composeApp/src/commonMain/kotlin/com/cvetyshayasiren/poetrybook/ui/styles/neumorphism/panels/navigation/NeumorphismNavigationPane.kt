package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugNavigationPane

@Composable
fun NeumorphismNavigationPane(modifier: Modifier = Modifier, isExpanded: Boolean)   {
    PlugNavigationPane(modifier = modifier, style = IsmStyle.NEU, isExpanded = isExpanded)
}