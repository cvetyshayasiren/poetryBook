package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.unit.IntSize
import kotlin.math.max
import kotlin.math.min

fun getArea(rangeI: IntRange, rangeJ: IntRange) =
    getArea(fromI = rangeI.first, untilI = rangeI.last, fromJ = rangeJ.first, untilJ = rangeJ.last)

fun getArea(fromI: Int = 0, untilI: Int, fromJ: Int = 0, untilJ: Int) =
    (fromJ until untilJ).flatMap { row -> (fromI until untilI).map { column -> column to row } }

fun Rect.union(other: Rect): Rect = Rect(
    min(left, other.left),
    min(top, other.top),
    max(right, other.right),
    max(bottom, other.bottom),
)

enum class FieldFigure { BASE, SQUARE, VERTICAL_LINE, HORIZONTAL_LINE, VERTICAL_RECT, HORIZONTAL_RECT;

    companion object {
        val ALL: FieldFigures = entries
        val VERTICAL = listOf(VERTICAL_LINE, VERTICAL_RECT)
        val HORIZONTAL = listOf(HORIZONTAL_LINE, HORIZONTAL_RECT)
        val LINES = listOf(HORIZONTAL_LINE, VERTICAL_LINE)
        val RECTS = listOf(HORIZONTAL_RECT, VERTICAL_RECT)
    }

}
typealias FieldFigures = List<FieldFigure>

fun IntSize.fieldFigure(): FieldFigure = when {
    width == 1 && height == 1 -> FieldFigure.BASE
    width == height -> FieldFigure.SQUARE
    width > height -> if(height == 1) FieldFigure.HORIZONTAL_LINE else FieldFigure.HORIZONTAL_RECT
    height > width -> if(width == 1) FieldFigure.VERTICAL_LINE else FieldFigure.VERTICAL_RECT
    else -> FieldFigure.BASE
}