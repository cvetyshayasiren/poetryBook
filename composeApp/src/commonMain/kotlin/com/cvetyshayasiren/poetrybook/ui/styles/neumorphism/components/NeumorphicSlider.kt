package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig
import com.materialkolor.ktx.lighten

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeumorphicSlider(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    color: Color = MaterialTheme.colorScheme.surfaceBright,
    onColor: Color = MaterialTheme.colorScheme.onSurface,
    label: String? = null,
    icon: ImageVector? = null,
    indicator: ((Float) -> String)? = { it.toInt().toString() },
    onValueChange: (Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fraction = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect { interaction ->
            when(interaction) {
                is DragInteraction.Start -> fraction.animateTo(1f)
                is DragInteraction.Stop -> fraction.animateTo(0f)
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(NeumorphismConfig.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NeumorphismConfig.smallPadding, Alignment.Start)
        ) {
            if(label != null) {
                Text(text = label, color = onColor)
            }

            indicator?.let { lambda ->
                val prettyValue = lambda(value)
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(NeumorphismConfig.smallPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(prettyValue, color = onColor)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NeumorphismConfig.smallPadding, Alignment.Start)
        ) {
            if(icon != null) {
                Box(
                    modifier = Modifier
                        .neumorphicDrop(color = color, shape = CircleShape)
                        .padding(NeumorphismConfig.smallPadding)
                    ,
                ) {
                    Icon(imageVector = icon, contentDescription = "slider")
                }
            }

            Slider(
                modifier = Modifier
                    .neumorphicDrop(
                        color = color.lighten(lerp(1f, 1.1f, fraction.value)),
                        shape = RoundedCornerShape(50)
                    ),
                interactionSource = interactionSource,
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                track = {
                    Box(modifier = Modifier.fillMaxWidth().clip(CircleShape))
                },
                thumb = {
                    Box(
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .size(36.dp)
                            .clip(CircleShape)
                            .neumorphicInner(
                                color = color,
                                shape = CircleShape
                            )
                    )
                }
            )
        }
    }
}