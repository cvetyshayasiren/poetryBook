package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugPagePane

@Composable
fun BrutalismPagePane(modifier: Modifier = Modifier) {
    PlugPagePane(modifier = modifier, style = IsmStyle.BRUT)
}