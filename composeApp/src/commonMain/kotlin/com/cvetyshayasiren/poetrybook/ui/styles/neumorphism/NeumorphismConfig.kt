package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object NeumorphismConfig {
    val bigPadding: Dp = 24.dp
    val mediumPadding: Dp = bigPadding / 2
    val smallPadding: Dp = mediumPadding / 2
    val defaultRoundedShape = RoundedCornerShape(50)
    val shadowRadius = 8.dp
    val shadowSpread = 0.dp
    val shadowOffset = 8.dp
    const val SHADOW_LIGHTEN_BY = 1.15f
    const val SHADOW_DARKEN_BY = 1.15f
    val navigationPaneHeight = 96.dp
}