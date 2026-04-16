package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern.BauhausFigure.PathBauhausFigure
import kotlin.jvm.JvmInline
import kotlin.random.Random

interface BauhausFigure {
    val onDraw: ContentDrawScope.() -> Unit

    class PathBauhausFigure(
        path: Path,
        color: Color = defaultColor,
        alpha: Float = defaultAlpha,
        style: DrawStyle = defaultStyle,
        colorFilter: ColorFilter? = defaultColorFilter,
        blendMode: BlendMode = defaultBlendMode
    ): BauhausFigure {
        override val onDraw: ContentDrawScope.() -> Unit = {
            drawPath(
                path = path.copy(),
                color = color,
                alpha = alpha,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
        }

        companion object {
            val EMPTY = PathBauhausFigure(path = Path())
            val defaultColor = Color.Unspecified
            val defaultAlpha = 1.0f
            val defaultStyle = Fill
            val defaultColorFilter: ColorFilter? = null
            val defaultBlendMode: BlendMode = DefaultBlendMode
            fun build(
                color: Color = defaultColor,
                alpha: Float = defaultAlpha,
                style: DrawStyle = defaultStyle,
                colorFilter: ColorFilter? = defaultColorFilter,
                blendMode: BlendMode = defaultBlendMode,
                block: Path.() -> Unit
            ) = Path().apply(block).toBauhausFigure(
                color = color,
                alpha = alpha,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
        }
    }
}

fun Path.toBauhausFigure(
    color: Color = PathBauhausFigure.defaultColor,
    alpha: Float = PathBauhausFigure.defaultAlpha,
    style: DrawStyle = PathBauhausFigure.defaultStyle,
    colorFilter: ColorFilter? = PathBauhausFigure.defaultColorFilter,
    blendMode: BlendMode = PathBauhausFigure.defaultBlendMode
) = PathBauhausFigure(
    path = copy(),
    color = color,
    alpha = alpha,
    style = style,
    colorFilter = colorFilter,
    blendMode = blendMode
)

//fun BauhausFigur.sizeModifier(scale: Float = 2f): BauhausFigur =
//    this.path.copy().apply {
//        transform(
//            matrix = Matrix().apply {
//                scale(scale, scale)
//            }
//        )
//    }.toBauhausFigur()
//
//fun BauhausFigur.turnModifier(count: Int): BauhausFigur =
//    this.path.copy().apply {
//        transform(
//            matrix = Matrix().apply {
//                resetToPivotedTransform(
//                    pivotX = .5f,
//                    pivotY = .5f,
//                    rotationZ = 90f * count
//                )
//            }
//        )
//    }.toBauhausFigur()

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
                if(!smooth && !sharp) { add(PathBauhausFigure.EMPTY) }
            }.random(random)
        }
    }

    enum class Smooth(val figure: BauhausFigure) {
        CIRCLE(figure = PathBauhausFigure.build { addOval(Rect(0f, 0f, 1f ,1f)) } ),
        SEMICIRCLE(
            figure = PathBauhausFigure.build {
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
        SQUARE(figure = PathBauhausFigure.build { addRect(Rect(0f, 0f, 1f ,1f)) }),
        TRIANGLE(
            figure = PathBauhausFigure.build {
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
}