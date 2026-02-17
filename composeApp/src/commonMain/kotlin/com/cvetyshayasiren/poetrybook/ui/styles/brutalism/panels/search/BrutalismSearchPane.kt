package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.panels.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism.PlugSearchPane

@Composable
fun BrutalismSearchPane(modifier: Modifier = Modifier) {
    PlugSearchPane(modifier = modifier, style = IsmStyle.BRUT)
}