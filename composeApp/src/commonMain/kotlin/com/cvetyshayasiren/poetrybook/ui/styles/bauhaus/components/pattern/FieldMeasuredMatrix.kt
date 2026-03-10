package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import kotlin.jvm.JvmInline
import kotlin.random.Random

@JvmInline
value class FieldMeasuredMatrixCell(val cell: Triple<Int, Int, Rect>) {
    fun getI() = cell.first
    fun getJ() = cell.second
    fun getRect() = cell.third

    fun intersect(other: FieldMeasuredMatrixCell): FieldMeasuredMatrixCell =
        FieldMeasuredMatrixCell(cell = cell.copy(third = cell.third.intersect(other.cell.third)))

    companion object {
        fun make(i: Int, j: Int, rect: Rect): FieldMeasuredMatrixCell = FieldMeasuredMatrixCell(cell = Triple(i, j, rect))
    }
}

@JvmInline
value class FieldMeasuredMatrix(val cells: List<FieldMeasuredMatrixCell>) {

    fun getWidth(): Int = cells.maxOf { it.getI() } + 1
    fun getHeight(): Int = cells.maxOf { it.getJ() } + 1
    fun getSize(): IntSize = IntSize(getWidth(), getHeight())

    companion object {
        val EMPTY = FieldMeasuredMatrix(listOf())
    }
}

fun List<FieldMeasuredMatrixCell>.toFieldMatrix(): FieldMeasuredMatrix = FieldMeasuredMatrix(cells = this)

class FieldMeasuredMatrixBuilder(
    val randomSeed: Int = 1,
    val matrix: FieldMeasuredMatrix
) {
    var mutableMatrixList = matrix.cells.toMutableList()
    val random = Random(randomSeed)

    fun build(): FieldMeasuredMatrix = mutableMatrixList.toFieldMatrix()

    fun shuffle() { mutableMatrixList.shuffled(random).toFieldMatrix() }

    fun test() {
        if(matrix.getWidth() > 2 && matrix.getHeight() > 2) {
            val old = matrix.cells.first()
            mutableMatrixList = matrix.cells.drop(1).plus(
                FieldMeasuredMatrixCell.make(
                    i = old.getI(), j = old.getJ(),
                    rect = Rect(
                        top = old.getRect().top,
                        left = old.getRect().left,
                        right = old.getRect().right + old.getRect().width,
                        bottom = old.getRect().bottom + old.getRect().height
                    )
                )
            ).toMutableList()
        }
    }
}