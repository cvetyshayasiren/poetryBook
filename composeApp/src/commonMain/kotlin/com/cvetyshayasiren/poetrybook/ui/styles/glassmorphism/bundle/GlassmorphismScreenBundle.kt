package com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.bundle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.GlassmorphismConfig
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.favorites.GlassmorphismFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.history.GlassmorphismHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.navigation.GlassmorphismNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.page.GlassmorphismPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.search.GlassmorphismSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.glassmorphism.panels.settings.GlassmorphismSettingsPane
import io.github.fletchmckee.liquid.liquefiable

class GlassmorphismScreenBundle: StyleScreenBundle {

    @get:Composable
    private val commonModifier get() = Modifier
        .liquefiable(GlassmorphismConfig.liquidState)

    @Composable
    override fun NavigationPane(modifier: Modifier) =
        GlassmorphismNavigationPane(modifier = modifier)

    @Composable
    override fun PagePane(modifier: Modifier) =
        GlassmorphismPagePane(modifier = modifier.then(commonModifier))

    @Composable
    override fun SettingsPane(modifier: Modifier) =
        GlassmorphismSettingsPane(modifier = modifier.then(commonModifier))

    @Composable
    override fun HistoryPane(modifier: Modifier) =
        GlassmorphismHistoryPane(modifier = modifier.then(commonModifier))

    @Composable
    override fun FavoritesPane(modifier: Modifier) =
        GlassmorphismFavoritesPane(modifier = modifier.then(commonModifier))

    @Composable
    override fun SearchPane(modifier: Modifier) =
        GlassmorphismSearchPane(modifier = modifier.then(commonModifier))
}