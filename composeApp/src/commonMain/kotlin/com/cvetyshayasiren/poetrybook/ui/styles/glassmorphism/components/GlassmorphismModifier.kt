package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.GlassmorphismConfig
import io.github.fletchmckee.liquid.liquid

@Composable
fun Modifier.glassy(
    tint: Color? = null,
    shape: Shape? = GlassmorphismConfig.defaultRoundedShape
): Modifier = this then Modifier
    .liquid(liquidState = GlassmorphismConfig.liquidState) {
        GlassmorphismConfig.glassy.invoke(this)
        tint?.let { this@liquid.tint = it }
        shape?.let { this@liquid.shape = it }
    }

@Composable
fun Modifier.glassyHazy(
    tint: Color? = null,
    shape: Shape? = GlassmorphismConfig.defaultRoundedShape
): Modifier = this then Modifier
    .liquid(liquidState = GlassmorphismConfig.liquidState) {
        GlassmorphismConfig.hazy.invoke(this)
        tint?.let { this@liquid.tint = it }
        shape?.let { this@liquid.shape = it }
    }