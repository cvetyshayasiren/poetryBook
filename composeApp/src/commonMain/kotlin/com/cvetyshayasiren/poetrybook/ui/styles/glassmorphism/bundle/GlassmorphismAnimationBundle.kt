package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.bundle

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationPair

class GlassmorphismAnimationBundle: AnimationBundle {
    override val navigate: AnimationPair = AnimationPair.build {
        val actionAlpha: Float = .2f
        val animationSpec: FiniteAnimationSpec<Float> = tween(
            durationMillis = 400,
            easing = FastOutLinearInEasing,
        )

        enter = fadeIn(initialAlpha = actionAlpha, animationSpec = animationSpec)
        exit = fadeOut(targetAlpha = actionAlpha, animationSpec = animationSpec)
    }

    override val swapThemeSeedColor: FiniteAnimationSpec<Color> = tween(
        durationMillis = 1200,
        easing = FastOutLinearInEasing,
    )
}