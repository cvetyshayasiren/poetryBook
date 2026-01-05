package com.cvetyshayasiren.poetrybook

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.cvetyshayasiren.poetrybook.domain.utils.randomExclude
import com.cvetyshayasiren.poetrybook.ui.adaptive.MainAdaptiveScreen
import com.cvetyshayasiren.poetrybook.ui.theme.PoetryBookTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
@Preview
fun App() {
    PoetryBookTheme {
        Surface {
            MainAdaptiveScreen()
        }
    }
}