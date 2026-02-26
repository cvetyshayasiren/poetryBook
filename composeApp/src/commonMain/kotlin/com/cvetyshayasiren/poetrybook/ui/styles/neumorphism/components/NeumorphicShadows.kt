package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten

@Composable
fun Modifier.neumorphicDrop(
    shape: Shape = NeumorphismConfig.defaultRoundedShape,
    radius: Dp = NeumorphismConfig.shadowRadius,
    color: Color = Color.Black,
    lightenBy: Float = NeumorphismConfig.SHADOW_LIGHTEN_BY,
    darkenBy: Float = NeumorphismConfig.SHADOW_DARKEN_BY,
    spread: Dp = NeumorphismConfig.shadowSpread,
    offset: Dp = NeumorphismConfig.shadowOffset,
    @FloatRange alpha: Float = 1f,
    blendMode: BlendMode = DefaultBlendMode
): Modifier =
    this then Modifier
        .dropShadow(
            shape,
            shadow = Shadow(
                radius = radius,
                color = color.lighten(lightenBy),
                spread = spread,
                offset = DpOffset(-offset, -offset),
                alpha = alpha,
                blendMode = blendMode
            )
        )
        .dropShadow(
            shape,
            shadow = Shadow(
                radius = radius,
                color = color.darken(darkenBy),
                spread = spread,
                offset = DpOffset(offset, offset),
                alpha = alpha,
                blendMode = blendMode
            )
        )
        .background(color, shape)

@Composable
fun Modifier.neumorphicInner(
    shape: Shape = NeumorphismConfig.defaultRoundedShape,
    radius: Dp = NeumorphismConfig.shadowRadius,
    color: Color = Color.Black,
    lightenBy: Float = NeumorphismConfig.SHADOW_LIGHTEN_BY,
    darkenBy: Float = NeumorphismConfig.SHADOW_DARKEN_BY,
    spread: Dp = NeumorphismConfig.shadowSpread,
    offset: Dp = NeumorphismConfig.shadowOffset,
    @FloatRange alpha: Float = 1f,
    blendMode: BlendMode = DefaultBlendMode
): Modifier =
    this then Modifier
        .background(color, shape)
        .innerShadow(
            shape,
            shadow = Shadow(
                radius = radius,
                color = color.lighten(lightenBy),
                spread = spread,
                offset = DpOffset(-offset, -offset),
                alpha = alpha,
                blendMode = blendMode
            )
        )
        .innerShadow(
            shape,
            shadow = Shadow(
                radius = radius,
                color = color.darken(darkenBy),
                spread = spread,
                offset = DpOffset(offset, offset),
                alpha = alpha,
                blendMode = blendMode
            )
        )