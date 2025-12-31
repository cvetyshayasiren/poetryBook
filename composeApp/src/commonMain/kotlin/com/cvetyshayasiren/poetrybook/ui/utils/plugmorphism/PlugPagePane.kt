package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle

@Composable
fun PlugPagePane(modifier: Modifier, style: IsmStyle) {
    PlugCommonPane(modifier, style, "PAGE")
}