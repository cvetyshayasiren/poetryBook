package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle

@Composable
fun PlugBookPane(modifier: Modifier, style: IsmStyle) {
    PlugCommonPane(modifier, style, "PLAYBOOK")
}