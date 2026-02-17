package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.bundle

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationBundle
import com.cvetyshayasiren.poetrybook.ui.styles.AnimationPair

class BauhausAnimationBundle: AnimationBundle {
    override val appearance: AnimationPair = AnimationPair.build {
        enter = slideInVertically(initialOffsetY = { -it })
        exit = slideOutVertically(targetOffsetY = { it })
    }

    override val colorAnimationSpec: FiniteAnimationSpec<Color> = spring(dampingRatio = Spring.DampingRatioLowBouncy)
}