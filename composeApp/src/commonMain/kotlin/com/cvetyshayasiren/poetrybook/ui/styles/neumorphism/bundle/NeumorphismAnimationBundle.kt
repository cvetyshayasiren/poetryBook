package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationPair

class NeumorphismAnimationBundle: AnimationBundle {
    override val navigate: AnimationPair = AnimationPair.build {
        val actionScale: Float = .8f
        enter = scaleIn(initialScale = actionScale) + fadeIn()
        exit = scaleOut(targetScale = actionScale) + fadeOut()
    }

    override val swapThemeSeedColor: FiniteAnimationSpec<Color> =
        tween(durationMillis = 1000, easing = FastOutSlowInEasing)
}