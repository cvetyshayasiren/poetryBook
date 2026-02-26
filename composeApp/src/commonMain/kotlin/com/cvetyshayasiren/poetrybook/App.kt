package com.cvetyshayasiren.poetrybook

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.splineBasedDecay
import androidx.compose.animation.unveilIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.ShapeLine
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onFirstVisible
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.ui.adaptive.MainAdaptiveScreen
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicIconButton
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.NeumorphicSlider
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.neumorphicDrop
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.components.neumorphicInner
import com.cvetyshayasiren.poetrybook.ui.theme.PoetryBookTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PoetryBookTheme {
        MainAdaptiveScreen()
//        NeuTest()
    }
}

@Composable
fun NeuTest() {
    val size = remember { mutableStateOf(64.dp) }
    val shape = remember { mutableStateOf(12) }
    val offset = remember { mutableStateOf(4.dp) }
    val radius = remember { mutableStateOf(6.dp) }
    val spread = remember { mutableStateOf(0.dp) }
    val darken = remember { mutableStateOf(1.15f) }
    val lighten = remember { mutableStateOf(1.15f) }
    val initColor = MaterialTheme.colorScheme.surfaceBright
    val color = remember { mutableStateOf(initColor) }
    val animatedColor = animateColorAsState(color.value)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(2f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "size",
                icon = Icons.Default.FormatSize,
                value = size.value.value,
                valueRange = 32f..256f,
            ) {
                size.value = it.dp
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "shape",
                icon = Icons.Default.ShapeLine,
                value = shape.value.toFloat(),
                valueRange = 0f..100f,
            ) {
                shape.value = it.toInt()
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "offset",
                icon = Icons.Default.Favorite,
                value = offset.value.value,
                valueRange = -100f..100f,
            ) {
                offset.value = it.dp
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "radius",
                icon = Icons.Default.Circle,
                value = radius.value.value,
                valueRange = 0f..100f,
            ) {
                radius.value = it.dp
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "spread",
                icon = Icons.Default.Spa,
                value = spread.value.value,
                valueRange = 0f..100f,
            ) {
                spread.value = it.dp
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "darken",
                icon = Icons.Default.DarkMode,
                value = darken.value,
                valueRange = 0f..2f,
                indicator = { it.toString() }
            ) {
                darken.value = it
            }

            NeumorphicSlider(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(6.dp),
                label = "lighten",
                icon = Icons.Default.Spa,
                value = lighten.value,
                valueRange = 0f..2f,
                indicator = { it.toString() }
            ) {
                lighten.value = it
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(.8f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(animatedColor.value)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size.value)
                    .neumorphicDrop(
                        shape = RoundedCornerShape(shape.value),
                        offset = offset.value,
                        radius = radius.value,
                        spread = spread.value,
                        color = color.value,
                        lightenBy = lighten.value,
                        darkenBy = darken.value
                    )
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size.value)
                    .neumorphicInner(
                        shape = RoundedCornerShape(shape.value),
                        offset = offset.value,
                        radius = radius.value,
                        spread = spread.value,
                        color = color.value
                    )
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }

            NeumorphicIconButton(
                modifier = Modifier
                    .size(size.value),
                color = color.value,
                onClick = {
                    color.value = Color(
                        red = (0..255).random(),
                        green = (0..255).random(),
                        blue = (0..255).random(),
                    )
                }
            ) {
                Icon(imageVector = Icons.Default.Colorize, contentDescription = null)
            }

            NeumorphicIconButton(
                modifier = Modifier
                    .size(size.value),
                color = color.value,
                onClick = { }
            ) {
                Icon(imageVector = Icons.Default.Colorize, contentDescription = null)
            }
        }
    }
}