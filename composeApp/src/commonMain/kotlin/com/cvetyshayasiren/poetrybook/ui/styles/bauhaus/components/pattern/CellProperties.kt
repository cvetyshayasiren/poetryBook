package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class CellProperties(
    val figure: BauhausFigure = CellPropertiesBuilder.defaultFigure,
    val pivotX: Float = CellPropertiesBuilder.defaultPivotX,
    val pivotY: Float = CellPropertiesBuilder.defaultPivotY,
    val translationX: Float = CellPropertiesBuilder.defaultTranslationX,
    val translationY: Float = CellPropertiesBuilder.defaultTranslationY,
    val translationXDp: Dp = CellPropertiesBuilder.defaultTranslationXDp,
    val translationYDp: Dp = CellPropertiesBuilder.defaultTranslationYDp,
    val scale: Float = CellPropertiesBuilder.defaultScale,
    val scaleX: Float = CellPropertiesBuilder.defaultScaleX,
    val scaleY: Float = CellPropertiesBuilder.defaultScaleY,
    val rotation: Float = CellPropertiesBuilder.defaultRotation,
    val color: Color = CellPropertiesBuilder.defaultColor,
    val style: DrawStyle = CellPropertiesBuilder.defaultStyle,
    val colorFilter: ColorFilter? = CellPropertiesBuilder.defaultColorFilter,
    val blendMode: BlendMode = CellPropertiesBuilder.defaultBlendMode,
    val padding: Dp? = CellPropertiesBuilder.defaultPadding
) {
    companion object

    fun draw(rect: Rect, drawScope: ContentDrawScope) = drawScope.apply {
        val matrix = Matrix().apply {
            resetToPivotedTransform(
                pivotX = pivotX,
                pivotY = pivotY,
                translationX = rect.center.x + translationX + translationXDp.toPx(),
                translationY = rect.center.y + translationY + translationYDp.toPx(),
                scaleX = rect.width * scaleX * scale,
                scaleY = rect.height * scaleY * scale,
                rotationZ = rotation
            )
        }

        padding?.let { padding ->
            val paddingScale = 1 - (padding / rect.width.toDp())
            if(paddingScale.isFinite()) {
                matrix.apply {
                    scale(paddingScale, paddingScale)
                }
            }
        }

        drawPath(
            path = figure.path.copy().apply { transform(matrix) },
            color = color,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
}

class CellPropertiesBuilder(
    i: Int = 0,
    j: Int = 0,
    randomSeed: Int = 1,
    var figure: BauhausFigure = defaultFigure,
    var pivotX: Float = defaultPivotX,
    var pivotY: Float = defaultPivotY,
    var translationX: Float = defaultTranslationX,
    var translationY: Float = defaultTranslationY,
    var translationXDp: Dp = defaultTranslationXDp,
    var translationYDp: Dp = defaultTranslationYDp,
    var scale: Float = defaultScale,
    var scaleX: Float = defaultScaleX,
    var scaleY: Float = defaultScaleY,
    var rotation: Float = defaultRotation,
    var color: Color = defaultColor,
    var style: DrawStyle = defaultStyle,
    var colorFilter: ColorFilter? = defaultColorFilter,
    var blendMode: BlendMode = defaultBlendMode,
    var padding: Dp? = defaultPadding,
) {
    val uniqueIndex = cantorPairIndex(i, j)
    val random = Random(uniqueIndex * randomSeed)

    fun build(): CellProperties = CellProperties(
        figure = figure,
        pivotX = pivotX,
        pivotY = pivotY,
        translationX = translationX,
        translationY = translationY,
        translationXDp = translationXDp,
        translationYDp = translationYDp,
        scale = scale,
        scaleX = scaleX,
        scaleY = scaleY,
        rotation = rotation,
        color = color,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
        padding = padding
    )

    private fun cantorPairIndex(i: Int, j: Int): Int  = (i + j) * (i + j + 1) / 2 + j

    companion object {
        val defaultFigure: BauhausFigure = FigurePack.Smooth.CIRCLE.figure
        val defaultPivotX: Float = .5f
        val defaultPivotY: Float = .5f
        val defaultTranslationX: Float = 0f
        val defaultTranslationY: Float = 0f
        val defaultTranslationXDp: Dp = 0.dp
        val defaultTranslationYDp: Dp = 0.dp
        val defaultScale: Float = 1f
        val defaultScaleX: Float = 1f
        val defaultScaleY: Float = 1f
        val defaultRotation: Float = 0f
        val defaultColor: Color = Color.Unspecified
        val defaultStyle: DrawStyle = Fill
        val defaultColorFilter: ColorFilter? = null
        val defaultBlendMode: BlendMode = DefaultBlendMode
        val defaultPadding: Dp? = null
    }
}

fun CellPropertiesBuilder.randomColor(): Color = Color(random.nextInt()).copy(alpha = 1f)
fun CellPropertiesBuilder.randomColor(colors: List<Color>): Color = colors[random.nextInt(colors.size)]
fun CellPropertiesBuilder.randomDegree(from: Int = -360, until: Int = 360): Float = random.nextInt(from, until).toFloat()
fun CellPropertiesBuilder.randomBoolean(): Boolean = random.nextBoolean()
fun CellPropertiesBuilder.withChance(chance: Float): Boolean = chance >= random.nextFloat()
fun CellPropertiesBuilder.randomFigure() = FigurePack.getRandom(seed = random.nextInt())
fun CellPropertiesBuilder.randomFigureFrom(
    smooth: Boolean = false,
    sharp: Boolean = false
) = FigurePack.getRandomFrom(seed = random.nextInt(), smooth = smooth, sharp = sharp)

