package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle

@Composable
fun PlugCommonPane(
    modifier: Modifier,
    style: IsmStyle,
    label: String,
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(style.label.labelRu)
        Text(label)
        content()
    }
}