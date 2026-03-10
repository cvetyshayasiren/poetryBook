package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

class BauhausPatternState(
    val fieldCalculations: FieldCalculations = FieldCalculations(),
    val cellsMatrix: CellsMatrix = CellsMatrix.Animated(),
    seed: Int? = null,
) {
    val seed: MutableState<Int> = mutableStateOf(seed ?: getRandomSeed())

    fun draw(size: Size, drawScope: ContentDrawScope) {
        fieldCalculations.calculate(seed.value, size).cells.forEach { cell ->
            cellsMatrix.calculate(
                i = cell.getI(), j = cell.getJ(), seed =  seed.value
            ).draw(rect = cell.getRect(), drawScope =  drawScope)
        }
    }

    fun setSeed(value: Int) { seed.value = value }
    fun setRandomSeed() { setSeed(getRandomSeed()) }
    private fun getRandomSeed() = (0..Int.MAX_VALUE).random()
}

@Composable
fun rememberBauhausPatternState(
    fieldCalculations: FieldCalculations = FieldCalculations(),
    cellsMatrix: CellsMatrix = CellsMatrix.Animated(),
    seed: Int? = null
) = remember { BauhausPatternState(fieldCalculations, cellsMatrix, seed) }
