package com.cvetyshayasiren.poetrybook

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.*
import com.cvetyshayasiren.poetrybook.ui.theme.PoetryBookTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PoetryBookTheme {
//        MainAdaptiveScreen()
        TestPattern()
    }
}

@Composable
fun TestPattern() {
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.error)
    val colors2 = listOf(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onSecondaryFixedVariant)

    val animatable = remember { Animatable(0f) }

    val state = rememberBauhausPatternState(
        fieldCalculations = FieldCalculations(
            fieldMeasurements = FieldMeasurements.Columns(8)
        ) {
            setLayer(0, layer().slice(4, FieldFigure.VERTICAL))
        },
        cellsMatrix = CellsMatrix.Animated(animatable = animatable) { cell, progress ->
            figure = FigurePack.getRandom(random)
            scale = lerp(1f, .8f, progress)
            rotation = lerp(randomDegree(), 0f, progress)
            padding = 4.dp
        },
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .bauhausPattern(state = state)
            .border(4.dp, Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                scope.launch {
                    when(animatable.value) {
                        0f -> animatable.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(dampingRatio = DampingRatioMediumBouncy)
                        )
                        1f -> animatable.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 1500)
                        )
                    }
                }
            }
        ) {
            Text("ANIMATE STATE")
        }
        Button(
            onClick = { state.setRandomSeed() }
        ) {
            Text("RANDOMISE SEED")
        }

        Text(text = "TEST PATTERN", color = Color.Black)
    }
}