package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationPair

class BrutalismAnimationBundle: AnimationBundle {
    override val navigate: AnimationPair = AnimationPair.build {
        val animationSpec: FiniteAnimationSpec<Float> = getAnimationSpec()

        enter = scaleIn(animationSpec = animationSpec) + fadeIn(animationSpec = animationSpec)
        exit = scaleOut(animationSpec = animationSpec) + fadeOut(animationSpec = animationSpec)
    }

    override val swapThemeSeedColor: FiniteAnimationSpec<Color> = getAnimationSpec()

    override val showSettingsPane: AnimationPair = AnimationPair.build {
        enter = expandHorizontally(animationSpec = getAnimationSpec(), expandFrom = Alignment.Start)
        exit = shrinkHorizontally(animationSpec = getAnimationSpec(), shrinkTowards = Alignment.Start)
    }

    override fun <T>getAnimationSpec(): FiniteAnimationSpec<T> = spring<T>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
 }