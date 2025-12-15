package com.cvetyshayasiren.poetrybook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.data.CommonRepositoryImpl
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import poetrybook.composeapp.generated.resources.Res
import poetrybook.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
            DataTest()
        }
    }
}

@Composable
fun DataTest() {
    val scope = rememberCoroutineScope()
    val data = remember { mutableStateOf(CommonRepositoryImpl().getDefault()) }

    Button(
        onClick = {
            scope.launch {
                data.value = CommonRepositoryImpl().getData()
            }
        }
    ) {
        Text("get DATA")
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(.8f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        item { Text("POETS ${data.value.size}") }
        items(items = data.value) {poet ->
            var expanded by remember { mutableStateOf(false) }
            Text(
                modifier = Modifier.clickable { expanded=!expanded },
                text = poet.name,
                color = MaterialTheme.colorScheme.primary
            )
            if(expanded) {
                poet.poems.forEach { poem ->
                    var expandedText by remember { mutableStateOf(false) }
                    Text(
                        modifier = Modifier.clickable { expandedText=!expandedText },
                        text = poem.title,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    if(expandedText) {
                        Text(
                            text = poem.text,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}