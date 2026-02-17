package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.defaultTransitionSpec
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.favorites.NeumorphismFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.history.NeumorphismHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.navigation.NeumorphismNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.page.NeumorphismPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.search.NeumorphismSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.settings.NeumorphismSettingsPane
import com.materialkolor.ktx.darken

class NeumorphismScreenBundle: StyleScreenBundle {

    @Composable
    override fun NavigationPane(modifier: Modifier, isExpanded: Boolean) =
        NeumorphismNavigationPane(modifier = modifier, isExpanded = isExpanded)

    @Composable
    override fun PagePane(modifier: Modifier) = NeumorphismPagePane(modifier = modifier)

    @Composable
    override fun SettingsPane(modifier: Modifier) = NeumorphismSettingsPane(modifier = modifier)

    @Composable
    override fun HistoryPane(modifier: Modifier) = NeumorphismHistoryPane(modifier = modifier)

    @Composable
    override fun FavoritesPane(modifier: Modifier) = NeumorphismFavoritesPane(modifier = modifier)

    @Composable
    override fun SearchPane(modifier: Modifier) = NeumorphismSearchPane(modifier = modifier)
}