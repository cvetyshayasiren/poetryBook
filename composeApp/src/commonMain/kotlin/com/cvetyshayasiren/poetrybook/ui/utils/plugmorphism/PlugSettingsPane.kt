package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle

@Composable
fun PlugSettingsPane(modifier: Modifier, style: IsmStyle, ) {
    PlugCommonPane(modifier, style, "SETTINGS")
}