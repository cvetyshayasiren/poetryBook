package com.cvetyshayasiren.poetrybook.ui.styles

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.scene.Scene
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.bundle.BauhausScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.brutalism.bundle.BrutalismScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.bundle.GlassmorphismScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle.NeumorphismScreenBundle

interface StyleScreenBundle {
    @Composable
    fun NavigationPane(
        modifier: Modifier = Modifier,
        isExpanded: Boolean
    )

    @Composable
    fun PagePane(modifier: Modifier = Modifier)

    @Composable
    fun SettingsPane(modifier: Modifier = Modifier)

    @Composable
    fun HistoryPane(modifier: Modifier = Modifier)

    @Composable
    fun FavoritesPane(modifier: Modifier = Modifier)

    @Composable
    fun SearchPane(modifier: Modifier = Modifier)
}

val IsmStyle.styleScreenBundle: StyleScreenBundle
    get() = when (this) {
        IsmStyle.NEU -> NeumorphismScreenBundle()
        IsmStyle.BRUT -> BrutalismScreenBundle()
        IsmStyle.BAU -> BauhausScreenBundle()
        IsmStyle.GLASS -> GlassmorphismScreenBundle()
    }