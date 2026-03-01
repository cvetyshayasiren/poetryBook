package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugPagePane

@Composable
fun NeumorphismPagePane(modifier: Modifier = Modifier) {
    PlugPagePane(modifier = modifier, style = IsmStyle.NEU)
}