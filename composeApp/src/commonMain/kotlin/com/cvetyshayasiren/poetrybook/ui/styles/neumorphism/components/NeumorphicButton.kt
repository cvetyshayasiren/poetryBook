package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig

@Composable
fun NeumorphicIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surfaceBright,
    clamped: Boolean = false,
    content: @Composable (() -> Unit)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fraction = remember { Animatable(0f) }

    LaunchedEffect(clamped) {
        when(clamped) {
            true -> fraction.animateTo(1f)
            false -> fraction.animateTo(0f)
        }
    }

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect { interaction ->
            println(interaction::class.simpleName)
            when(interaction) {
                is PressInteraction.Press -> fraction.animateTo(1f)
                is PressInteraction.Release -> fraction.animateTo(0f)
                is HoverInteraction.Exit -> fraction.animateTo(0f)
            }
        }
    }

    IconButton(
        modifier = Modifier
            .graphicsLayer {
                scaleX = lerp(1f, .8f, fraction.value)
                scaleY = lerp(1f, .8f, fraction.value)
            }
            .neumorphicDrop(
                color = color,
                shape = CircleShape,
                offset = lerp(NeumorphismConfig.shadowOffset, (-16).dp, fraction.value)
            ) then modifier,
        enabled = !clamped,
        interactionSource = interactionSource,
        onClick = onClick,
        content = content
    )
}

