package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.jvm.JvmInline
import kotlin.random.Random

fun Path.toBauhausFigure() = BauhausFigure(this)

@JvmInline
value class BauhausFigure(val path: Path) {

    companion object {
        val EMPTY = BauhausFigure(Path())
        fun build(block: Path.() -> Unit): BauhausFigure = Path().apply(block).toBauhausFigure()
    }
}

fun BauhausFigure.sizeModifier(scale: Float = 2f): BauhausFigure =
    this.path.copy().apply {
        transform(
            matrix = Matrix().apply {
                scale(scale, scale)
            }
        )
    }.toBauhausFigure()

fun BauhausFigure.turnModifier(count: Int): BauhausFigure =
    this.path.copy().apply {
        transform(
            matrix = Matrix().apply {
                resetToPivotedTransform(
                    pivotX = .5f,
                    pivotY = .5f,
                    rotationZ = 90f * count
                )
            }
        )
    }.toBauhausFigure()

class FigurePack {
    companion object {
        fun getRandom(random: Random) = getRandomFrom(random = random, smooth = true, sharp = true)

        fun getRandomFrom(
            random: Random,
            smooth: Boolean = false,
            sharp: Boolean = false
        ): BauhausFigure {
            return buildList {
                if(smooth) { add(Smooth.getRandom(random)) }
                if(sharp) { add(Sharp.getRandom(random)) }
                if(!smooth && !sharp) { add(BauhausFigure.EMPTY) }
            }.random(random)
        }
    }

    enum class Smooth(val figure: BauhausFigure) {
        CIRCLE(figure = BauhausFigure.build { addOval(Rect(0f, 0f, 1f ,1f)) }),
        SEMICIRCLE(
            figure = BauhausFigure.build {
                addArc(
                    oval = Rect(0f, 0f, 1f ,1f),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 180f,
                )
            }
        );
        companion object {
            fun getRandom(random: Random): BauhausFigure = entries.random(random).figure
        }
    }

    enum class Sharp(val figure: BauhausFigure) {
        SQUARE(figure = BauhausFigure.build { addRect(Rect(0f, 0f, 1f ,1f)) }),
        TRIANGLE(
            figure = BauhausFigure.build {
                moveTo(0f, 0f)
                lineTo(1f, 1f)
                lineTo(0f, 1f)
                lineTo(0f, 0f)
            }
        );
        companion object {
            fun getRandom(random: Random): BauhausFigure = entries.random(random).figure
        }
    }

    class Line {


        companion object {

        }
    }
}

interface BauFigure {
    val onDraw: ContentDrawScope.() -> Unit

    class PathFig(): BauFigure {
        override val onDraw: ContentDrawScope.() -> Unit = {
            val path = Path().apply { addRect(Rect(0f, 0f, 1f ,1f)) }

            drawPath(
                path = path,
                color = Color.Green
            )
        }
    }
}