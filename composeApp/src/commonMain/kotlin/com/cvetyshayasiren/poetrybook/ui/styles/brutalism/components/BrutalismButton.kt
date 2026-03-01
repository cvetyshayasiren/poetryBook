package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.BrutalismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle.BrutalismAnimationBundle

@Composable
fun BrutalismIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color = MaterialTheme.colorScheme.inversePrimary,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    content: @Composable (() -> Unit)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fraction = remember { Animatable(0f) }
    val animationSpec = remember { BrutalismAnimationBundle().getAnimationSpec<Float>() }

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect { interaction ->
            when(interaction) {
                is PressInteraction.Press -> fraction.animateTo(targetValue = 1f)
                is PressInteraction.Release -> fraction.animateTo(targetValue = 0f, animationSpec = animationSpec)
                is HoverInteraction.Exit -> fraction.animateTo(targetValue = 0f, animationSpec = animationSpec)
            }
        }
    }

    IconButton(
        modifier = Modifier
            .graphicsLayer {
                scaleX = lerp(1f, .9f, fraction.value)
                scaleY = lerp(1f, .9f, fraction.value)
            }
            .brutalShadow(
                backgroundColor = buttonColor,
                shadowColor = shadowColor,
                borderColor = borderColor,
                offset = lerp(BrutalismConfig.borderWidth, 0.dp, fraction.value)
                    .let { DpOffset(it, it) }
            )
            .then(modifier),
        interactionSource = interactionSource,
        onClick = onClick,
        shape = RoundedCornerShape(0),
        content = content
    )
}