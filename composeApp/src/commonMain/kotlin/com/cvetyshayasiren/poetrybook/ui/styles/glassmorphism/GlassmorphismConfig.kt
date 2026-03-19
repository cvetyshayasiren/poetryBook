package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidScope
import io.github.fletchmckee.liquid.LiquidState

object GlassmorphismConfig {
    val liquidState = LiquidState()

    val glassy: LiquidScope.() -> Unit = {
        frost = 0.dp
        refraction = .25f
        curve = .25f
        edge = .05f
        saturation = 1f
        dispersion = 0f
        contrast = 1f
    }

    val hazy: LiquidScope.() -> Unit = {
        frost = 20.dp
        refraction = 0f
        curve = .25f
        edge = 0f
        saturation = 1f
        dispersion = 0f
        contrast = 1f
    }

    val bigPadding: Dp = 24.dp
    val mediumPadding: Dp = bigPadding / 2
    val smallPadding: Dp = mediumPadding / 2
    val defaultRoundedShape = CircleShape
}