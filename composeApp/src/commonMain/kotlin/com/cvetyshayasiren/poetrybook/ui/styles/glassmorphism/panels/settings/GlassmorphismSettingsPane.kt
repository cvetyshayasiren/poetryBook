package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugSettingsPane

@Composable
fun GlassmorphismSettingsPane(modifier: Modifier = Modifier) {
    PlugSettingsPane(modifier = modifier, style = IsmStyle.GLASS)
}