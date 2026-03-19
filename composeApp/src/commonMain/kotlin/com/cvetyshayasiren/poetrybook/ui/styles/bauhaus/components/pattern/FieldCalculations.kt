package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Size

class FieldCalculations(
    val fieldMeasurements: FieldMeasurements = FieldMeasurements.Grid(),
    val fieldMatrixTransform: (FieldMeasuredMatrixBuilder.() -> Unit) = {  }
) {
    private var cachedFieldMeasuredMatrix: FieldMeasuredMatrix = FieldMeasuredMatrix.EMPTY
    private var cachedSize = Size.Zero
    private var cachedSeed = 0

    fun calculate(seed: Int, size: Size): FieldMeasuredMatrix {
        if(seed != cachedSeed || size != cachedSize) {
            cachedSeed = seed
            cachedSize = size
            cachedFieldMeasuredMatrix = FieldMeasuredMatrixBuilder(
                randomSeed = seed,
                matrix = fieldMeasurements.calculate(size)
            ).apply { fieldMatrixTransform() }.build()
        }
        return cachedFieldMeasuredMatrix
    }
}