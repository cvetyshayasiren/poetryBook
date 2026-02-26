package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.BrutalismConfig

@Composable
fun Modifier.brutalShadow(
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color = MaterialTheme.colorScheme.secondary,
    borderColor: Color = MaterialTheme.colorScheme.tertiary,
    shape: Shape = BrutalismConfig.defaultRoundedShape,
    width: Dp = BrutalismConfig.shadowWidth
): Modifier = this then Modifier
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 0.dp,
            spread = 0.dp,
            color = shadowColor,
            offset = DpOffset(width, width)
        )
    )
    .border(width = width, color = borderColor, shape = shape)
    .clip(shape)
    .background(backgroundColor)