package com.cvetyshayasiren.poetrybook.ui.styles

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.bundle.BauhausAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle.BrutalismAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.bundle.GlassmorphismAnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle.NeumorphismAnimationBundle

interface AnimationBundle {
    val navigate: AnimationPair get() = AnimationPair()
    val swapStyle: AnimationPair get() = navigate
    val swapThemeSeedColor: FiniteAnimationSpec<Color> get() = tween(durationMillis = 1000)
    val showSettingsPane get() = AnimationPair(
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    )
    val swapNavigationPane get() = AnimationPair()
    fun <T>getAnimationSpec(): FiniteAnimationSpec<T> = tween()
}

data class AnimationPair(
    val enter: EnterTransition = defaultEnter,
    val exit: ExitTransition = defaultExit,
) {
    val contentTransform = enter togetherWith exit

    companion object {
        val defaultEnter = fadeIn()
        val defaultExit = fadeOut()

        class Builder {
            var enter: EnterTransition = defaultEnter
            var exit: ExitTransition = defaultExit

            fun build(): AnimationPair = AnimationPair(enter = enter, exit = exit)
        }

        fun build(block: Builder.() -> Unit): AnimationPair =
            Builder().also { it.block() }.build()
    }
}

val IsmStyle.animationBundle: AnimationBundle
    get() = when(this) {
        IsmStyle.NEU -> NeumorphismAnimationBundle()
        IsmStyle.BRUT -> BrutalismAnimationBundle()
        IsmStyle.BAU -> BauhausAnimationBundle()
        IsmStyle.GLASS -> GlassmorphismAnimationBundle()
    }