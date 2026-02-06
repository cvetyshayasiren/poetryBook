package com.cvetyshayasiren.poetrybook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.ui.adaptive.MainAdaptiveScreen
import com.cvetyshayasiren.poetrybook.ui.theme.PoetryBookTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PoetryBookTheme {
        Surface {
            MainAdaptiveScreen()
        }
    }
}

@Composable
fun ScrollTest() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        repeat(100) { main ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(600.dp)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                TestItemBox(tag = "MAIN № $main", color = MaterialTheme.colorScheme.primary)
                repeat(100) { down ->
                    TestItemBox(tag = "item № $down")
                }
            }
        }
    }
}

@Composable
fun TestItemBox(tag: String, color: Color = MaterialTheme.colorScheme.primaryContainer) {
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = tag,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}