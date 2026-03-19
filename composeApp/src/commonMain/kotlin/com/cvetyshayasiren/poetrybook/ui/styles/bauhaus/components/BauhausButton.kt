package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.bundle.BauhausAnimationBundle

@Composable
fun BauhausIconButton(
    modifier: Modifier = Modifier,
    clamped: Boolean = false,
    icon: ImageVector = Icons.Filled.Favorite,
    contentDescription: String? = null,
    tint: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fraction = remember { Animatable(0f) }
    val animationSpec = remember { BauhausAnimationBundle().getAnimationSpec<Float>() }

    LaunchedEffect(clamped) {
        when(clamped) {
            true -> fraction.animateTo(1f)
            false -> fraction.animateTo(0f)
        }
    }

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect { interaction ->
            when(interaction) {
                is PressInteraction.Press -> fraction.animateTo(targetValue = 1f, animationSpec = animationSpec)
                is PressInteraction.Release -> fraction.animateTo(targetValue = 0f, animationSpec = animationSpec)
                is HoverInteraction.Exit -> fraction.animateTo(targetValue = 0f, animationSpec = animationSpec)
            }
        }
    }

    Icon(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                enabled = !clamped,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        imageVector = icon,
        contentDescription = contentDescription,
        tint = tint,
    )
}