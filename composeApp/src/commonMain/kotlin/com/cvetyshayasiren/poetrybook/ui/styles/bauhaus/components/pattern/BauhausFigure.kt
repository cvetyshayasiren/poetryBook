package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
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

class FigurePack {
    companion object {
        fun getRandom(seed: Int) = getRandomFrom(seed = seed, smooth = true, sharp = true)

        fun getRandomFrom(
            seed: Int,
            smooth: Boolean = false,
            sharp: Boolean = false
        ): BauhausFigure {
            return buildList {
                if(smooth) { add(Smooth.getRandom(seed)) }
                if(sharp) { add(Sharp.getRandom(seed)) }
                if(!smooth && !sharp) { add(BauhausFigure.EMPTY) }
            }.random(Random(seed))
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
            fun getRandom(seed: Int): BauhausFigure = entries.random(Random(seed)).figure
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
            fun getRandom(seed: Int): BauhausFigure = entries.random(Random(seed)).figure
        }
    }

    class Line {


        companion object {

        }
    }
}