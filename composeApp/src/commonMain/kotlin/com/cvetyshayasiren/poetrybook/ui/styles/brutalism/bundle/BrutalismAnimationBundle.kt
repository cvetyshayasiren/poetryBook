package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationPair

class BrutalismAnimationBundle: AnimationBundle {
    override val navigate: AnimationPair = AnimationPair.build {
        val animationSpec: FiniteAnimationSpec<Float> = getSpringSpec()

        enter = scaleIn(animationSpec = animationSpec) + fadeIn(animationSpec = animationSpec)
        exit = scaleOut(animationSpec = animationSpec) + fadeOut(animationSpec = animationSpec)
    }

    override val swapThemeSeedColor: FiniteAnimationSpec<Color> = getSpringSpec()

    override val showSettingsPane: AnimationPair = AnimationPair.build {
        enter = expandHorizontally(animationSpec = getSpringSpec(), expandFrom = Alignment.Start)
        exit = shrinkHorizontally(animationSpec = getSpringSpec(), shrinkTowards = Alignment.Start)
    }

    private fun <T>getSpringSpec() = spring<T>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
 }