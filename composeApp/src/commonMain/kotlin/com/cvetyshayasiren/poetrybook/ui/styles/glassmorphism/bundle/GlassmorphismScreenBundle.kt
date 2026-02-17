package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.bundle

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.scene.Scene
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.favorites.GlassmorphismFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.history.GlassmorphismHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.navigation.GlassmorphismNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.page.GlassmorphismPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.search.GlassmorphismSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.settings.GlassmorphismSettingsPane

class GlassmorphismScreenBundle: StyleScreenBundle {
    @Composable
    override fun NavigationPane(modifier: Modifier, isExpanded: Boolean) =
        GlassmorphismNavigationPane(modifier = modifier, isExpanded = isExpanded)

    @Composable
    override fun PagePane(modifier: Modifier) = GlassmorphismPagePane(modifier = modifier)

    @Composable
    override fun SettingsPane(modifier: Modifier) = GlassmorphismSettingsPane(modifier = modifier)

    @Composable
    override fun HistoryPane(modifier: Modifier) = GlassmorphismHistoryPane(modifier = modifier)

    @Composable
    override fun FavoritesPane(modifier: Modifier) = GlassmorphismFavoritesPane(modifier = modifier)

    @Composable
    override fun SearchPane(modifier: Modifier) = GlassmorphismSearchPane(modifier = modifier)
}