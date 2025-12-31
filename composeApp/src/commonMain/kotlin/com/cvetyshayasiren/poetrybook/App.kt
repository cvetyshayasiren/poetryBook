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
    LaunchedEffect(Unit) {
        testSeq()
    }
    PoetryBookTheme {
        MainAdaptiveScreen()
    }
}

fun testSeq() {
    val poetsSequence = PoetsSequence(
        value = mapOf(
            0 to PoemsSequence(value = listOf(0, 1, 2)),
            1 to PoemsSequence(value = listOf(0, 1, 2, 4, 5, 7, 8)),
            22 to PoemsSequence(value = listOf(2, 4, 5, 7, 8, 12, 22, 23, 56))
        )
    )
    println("poetsSequence: ${poetsSequence.value}")
    val dataPoetsSequence = DataPoetsSequence.fromPoetSequence(poetsSequence)
    println("dataPoetsSequence: ${dataPoetsSequence.value}")

    val encoded = Json.encodeToString<DataPoetsSequence>(dataPoetsSequence)
    println(encoded)
    val decoded = Json.decodeFromString<DataPoetsSequence>(encoded)
    println(decoded.value)

    val finally = decoded.toPoetsSequence()
    println("finally: ${finally.value}")

    println(
        when(poetsSequence == finally) {
            true -> { "PASSED!"}
            false -> { "ERROR!" }
        }
    )
}