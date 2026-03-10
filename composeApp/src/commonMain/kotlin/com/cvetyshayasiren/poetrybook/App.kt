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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.CellsMatrix
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.FieldCalculations
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.FieldMeasurements
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.FigurePack
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.bauhausPattern
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.randomFigure
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.randomColor
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.randomDegree
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.randomFigureFrom
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.rememberBauhausPatternState
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.sizeModifier
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
    val colors =
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.tertiary)

    val animatable = remember { Animatable(0f) }

    val state = rememberBauhausPatternState(
        fieldCalculations = FieldCalculations(
            fieldMeasurements = FieldMeasurements.Columns(5)
        ) {
//            test()
        },
        cellsMatrix = CellsMatrix.Animated(animatable = animatable) { i, j, progress ->
            figure = randomFigure().sizeModifier()
            rotation = lerp(randomDegree(), randomDegree(), progress)
            scale = lerp(.2f, 1f, progress)
            color = lerp(randomColor(colors), randomColor(colors), progress)
            style = Fill
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
        Text("TEST PATTERN")
    }
}