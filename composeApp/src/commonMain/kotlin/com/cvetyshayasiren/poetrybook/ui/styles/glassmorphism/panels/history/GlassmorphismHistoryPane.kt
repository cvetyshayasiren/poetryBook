package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugHistoryPane

@Composable
fun GlassmorphismHistoryPane(modifier: Modifier = Modifier) {
    PlugHistoryPane(modifier = modifier, style = IsmStyle.GLASS)
}