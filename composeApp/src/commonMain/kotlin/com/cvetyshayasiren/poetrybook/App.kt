package com.cvetyshayasiren.poetrybook

import androidx.compose.runtime.*
import com.cvetyshayasiren.poetrybook.data.models.DataPoemsSequence
import com.cvetyshayasiren.poetrybook.data.models.DataPoetsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.PoetsSequence
import com.cvetyshayasiren.poetrybook.ui.adaptive.MainAdaptiveScreen
import com.cvetyshayasiren.poetrybook.ui.theme.PoetryBookTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PoetryBookTheme {
        MainAdaptiveScreen()
    }
}