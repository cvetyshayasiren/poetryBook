package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

interface CellsMatrix {
    fun calculate(i: Int, j: Int, seed: Int): CellProperties

    class Static(
        val cellMatrix: CellPropertiesBuilder.(i: Int, j: Int) -> Unit = { _, _ -> }
    ): CellsMatrix {
        override fun calculate(i: Int, j: Int, seed: Int): CellProperties =
            CellPropertiesBuilder(randomSeed = seed).apply { cellMatrix(i, j) }.build()
    }

    class Animated(
        val animatable: Animatable<Float, AnimationVector1D> = Animatable(0f),
        val transitionMatrix: CellPropertiesBuilder.(i: Int, j: Int, progress: Float) -> Unit = { _, _, _ -> }
    ): CellsMatrix {
        override fun calculate(i: Int, j: Int, seed: Int): CellProperties =
            CellPropertiesBuilder(i = i, j = j, randomSeed = seed)
                .apply { transitionMatrix(i,j,animatable.value) }.build()
    }
}