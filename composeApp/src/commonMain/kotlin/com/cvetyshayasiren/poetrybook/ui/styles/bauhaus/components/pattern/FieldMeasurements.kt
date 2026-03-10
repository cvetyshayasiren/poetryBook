package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toIntSize
import androidx.compose.ui.unit.toOffset
import kotlin.math.min

interface FieldMeasurements {
    val alignment: Alignment

    fun calculate(size: Size): FieldMeasuredMatrix

    class Grid(
        val columns: Int = 2,
        val rows: Int = 2,
        override val alignment: Alignment = Alignment.Center
    ): FieldMeasurements {
        init {
            require(columns > 0 && rows > 0) { "columns and rows must be greater than 0" }
        }

        override fun calculate(size: Size): FieldMeasuredMatrix {
            val cellSize = min(size.width / columns, size.height / rows)
            return defaultCalculate(size, cellSize, columns, rows)
        }
    }

    class Columns(val columns: Int = 2, override val alignment: Alignment = Alignment.Center): FieldMeasurements {
        init { require(columns > 0) { "columns must be greater than 0" } }
        override fun calculate(size: Size): FieldMeasuredMatrix {
            val cellSize = size.width / columns
            val rows = (size.height / cellSize).toInt().coerceAtLeast(1)
            return defaultCalculate(size, cellSize, columns, rows)
        }
    }

    class Rows(val rows: Int = 2, override val alignment: Alignment = Alignment.Center): FieldMeasurements {
        init { require(rows > 0) { "rows must be greater than 0" } }
        override fun calculate(size: Size): FieldMeasuredMatrix {
            val cellSize = size.height / rows
            val columns = (size.width / cellSize).toInt().coerceAtLeast(1)
            return defaultCalculate(size, cellSize, columns, rows)
        }
    }

    class Cell(val cellSize: Float = 48f, override val alignment: Alignment = Alignment.Center): FieldMeasurements {
        init {
            require(cellSize > 0f) { "cellSize must be greater than zero" }
        }

        override fun calculate(size: Size): FieldMeasuredMatrix {
            val columns: Int = (size.width / cellSize).toInt().coerceAtLeast(1)
            val rows: Int = (size.height / cellSize).toInt().coerceAtLeast(1)
            return defaultCalculate(size, cellSize, columns, rows)
        }
    }

    class Uno(override val alignment: Alignment = Alignment.Center): FieldMeasurements {
        override fun calculate(size: Size): FieldMeasuredMatrix =
            FieldMeasuredMatrix(
                cells = listOf(
                    FieldMeasuredMatrixCell.make(0,0, Rect(center = size.center, radius = size.minDimension))
                )
            )
    }
}

private fun FieldMeasurements.defaultCalculate(
    size: Size,
    cellSize: Float,
    columns: Int,
    rows: Int
): FieldMeasuredMatrix {
    val contentIntSize = Size(cellSize * columns, cellSize * rows).toIntSize()
    val (offsetX, offsetY) = alignment
        .align(contentIntSize, size.toIntSize(), LayoutDirection.Ltr)
        .toOffset() + Offset(cellSize / 2, cellSize / 2)

    return buildList<FieldMeasuredMatrixCell> {
        getCellsList(columns, rows).forEach { (i, j) ->
            val centerX = offsetX + i * cellSize
            val centerY = offsetY + j * cellSize
            add(
                FieldMeasuredMatrixCell.make(
                    i, j, Rect(
                        center = Offset(centerX, centerY),
                        radius = cellSize / 2
                    )
                )
            )
        }
    }.toFieldMatrix()
}

private fun FieldMeasurements.getCellsList(columns: Int, rows: Int) =
    (0 until rows).flatMap { row -> (0 until columns).map { column -> column to row } }