package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

interface CellsMatrix {
    fun calculate(cell: FieldMeasuredCell, layer: Int, seed: Int): CellProperties

    class Static(
        val cellMatrix: CellPropertiesBuilder.(cell: FieldMeasuredCell) -> Unit = { _, _ -> }
    ): CellsMatrix {
        override fun calculate(cell: FieldMeasuredCell, layer: Int, seed: Int): CellProperties =
            CellPropertiesBuilder(randomSeed = seed, layer = layer).apply { cellMatrix(cell) }.build()
    }

    class Animated(
        val animatable: Animatable<Float, AnimationVector1D> = Animatable(0f),
        val transitionMatrix: CellPropertiesBuilder.(cell: FieldMeasuredCell, progress: Float) -> Unit = { _, _, _ -> }
    ): CellsMatrix {
        override fun calculate(cell: FieldMeasuredCell, layer: Int, seed: Int): CellProperties =
            CellPropertiesBuilder(i = cell.i, j = cell.j, randomSeed = seed, layer = layer)
                .apply { transitionMatrix(cell,animatable.value) }.build()
    }
}