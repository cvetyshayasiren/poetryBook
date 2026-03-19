package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import kotlin.jvm.JvmInline
import kotlin.random.Random

data class FieldMeasuredCell(
    val i: Int, val j: Int, val rect: Rect,
    val baseSize: IntSize = IntSize(1, 1)
) {
    fun isBase(): Boolean = baseSize == IntSize(1, 1)

    fun figure(): FieldFigure = baseSize.fieldFigure()
    fun union(other: FieldMeasuredCell): FieldMeasuredCell = copy(
        rect = rect.union(other.rect),
        baseSize = IntSize(
            width = when(this.i < other.i) {
                true -> other.i - this.i + other.baseSize.width
                false -> other.i - this.i + this.baseSize.width
            },
            height = when(this.j < other.j) {
                true -> other.j - this.j + other.baseSize.height
                false -> other.j - this.j + this.baseSize.height
            }
        )
    )
}

typealias FieldLayer = List<FieldMeasuredCell>
private val emptyFieldLayer: FieldLayer = emptyList()

@JvmInline
value class FieldMeasuredMatrix(val matrixMap: MutableMap<Int, FieldLayer>) {
    companion object {
        val EMPTY: FieldMeasuredMatrix = FieldMeasuredMatrix(mutableMapOf())
        fun fromBaseLayer(layer: FieldLayer): FieldMeasuredMatrix = FieldMeasuredMatrix(
            matrixMap = mutableMapOf(0 to layer)
        )
    }
}

class FieldMeasuredMatrixBuilder(
    val randomSeed: Int,
    var matrix: FieldMeasuredMatrix
) {
    val initialBaseLayer = layer(0)
    val random = Random(randomSeed)

    fun build(): FieldMeasuredMatrix = matrix

    fun layer(zIndex: Int = 0): FieldLayer = matrix.matrixMap[zIndex] ?: emptyFieldLayer

    fun setLayer(zIndex: Int = 0, layer: FieldLayer) { matrix.matrixMap[zIndex] = layer }
    fun addOnLayer(zIndex: Int, layer: FieldLayer) {
        matrix.matrixMap[zIndex] = ((matrix.matrixMap[zIndex] ?: emptyFieldLayer) + layer)
    }

    fun shuffle(vararg zIndexes: Int) {
        when(zIndexes.isEmpty()) {
            true -> matrix.matrixMap.forEach { (zIndex, layer) ->
                matrix.matrixMap[zIndex] = layer.shuffled(random)
            }
            false -> zIndexes.forEach { zIndex ->
                matrix.matrixMap[zIndex]?.let { fieldLayer ->
                    matrix.matrixMap[zIndex] = fieldLayer.shuffled(random)
                }
            }
        }
    }

    fun FieldLayer.prepareLayer(sample: FieldLayer = initialBaseLayer): FieldLayer {
        val sample = sample.toMutableList()
        forEach { cell ->
            if(cell.isBase()) { sample.remove(cell) }
        }
        return sample
    }

    fun FieldLayer.slice(maxSideSize: Int = 2, figures: FieldFigures = FieldFigure.ALL): FieldLayer {
        val layerToSlice = this.toMutableList()
        val newLayer = mutableListOf<FieldMeasuredCell>()
        while(layerToSlice.isNotEmpty()) {
            val nextIndex = layerToSlice.lastIndex.let {
                if(it > 0) Random.nextInt(0, it) else 0
            }
            val startCell = layerToSlice[nextIndex]
            val finishCell = randomPossibleFinishCell(startCell, layerToSlice,maxSideSize, figures)
            layerToSlice.removeAll { cell ->
                (cell.i in (startCell.i..finishCell.i)) && (cell.j in (startCell.j..finishCell.j))
            }
            val unionCell = startCell.union(finishCell)
            newLayer.add(unionCell)
        }
        return newLayer
    }

    private fun randomPossibleFinishCell(
        cell: FieldMeasuredCell,
        layer: FieldLayer,
        maxSize: Int,
        figures: FieldFigures
    ): FieldMeasuredCell {
        val cellI = cell.i
        val cellJ = cell.j

        val possibleFinish =
            getArea(fromI = cellI, untilI = cellI + maxSize, fromJ = cellJ, untilJ = cellJ + maxSize).toMutableList()

        return buildList {
            while(possibleFinish.isNotEmpty()) {
                val (pI, pJ) = possibleFinish.first()
                val find = layer.find { it.i == pI && it.j == pJ }
                when(find == null) {
                    true -> possibleFinish.removeAll { (rI, rJ) -> rI >= pI && rJ >= pJ }
                    false -> {
                        possibleFinish.removeFirst()
                        if(IntSize(pI - cellI, pJ - cellJ).fieldFigure() in figures) { add(find) }
                    }
                }
            }
            if(isEmpty()) { add(cell) }
        }.random(random)
    }
}