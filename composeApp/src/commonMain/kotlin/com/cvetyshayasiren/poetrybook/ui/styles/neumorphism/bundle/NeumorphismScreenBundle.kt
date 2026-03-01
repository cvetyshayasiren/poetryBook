package com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.bundle

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.cvetyshayasiren.poetrybook.ui.navigation.isExpanded
import com.cvetyshayasiren.poetrybook.ui.styles.StyleScreenBundle
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.NeumorphismConfig.navigationPaneHeight
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.favorites.NeumorphismFavoritesPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.history.NeumorphismHistoryPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.navigation.NeumorphismNavigationPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.page.NeumorphismPagePane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.search.NeumorphismSearchPane
import com.cvetyshayasiren.poetrybook.ui.styles.neumorphism.panels.settings.NeumorphismSettingsPane

class NeumorphismScreenBundle: StyleScreenBundle {

    @get:Composable
    private val commonModifier get() = Modifier
        .padding(bottom = navigationPaneHeight)

    @get:Composable
    private val settingsPaneModifier get() = Modifier
        .then(if(WindowSizeClass.isExpanded()) Modifier else commonModifier)


    @Composable
    override fun NavigationPane(modifier: Modifier) =
        NeumorphismNavigationPane(modifier = modifier)

    @Composable
    override fun PagePane(modifier: Modifier) =
        NeumorphismPagePane(modifier = modifier.then(commonModifier))

    @Composable
    override fun SettingsPane(modifier: Modifier) =
        NeumorphismSettingsPane(modifier = modifier.then(settingsPaneModifier))

    @Composable
    override fun HistoryPane(modifier: Modifier) =
        NeumorphismHistoryPane(modifier = modifier.then(commonModifier))

    @Composable
    override fun FavoritesPane(modifier: Modifier) =
        NeumorphismFavoritesPane(modifier = modifier.then(commonModifier))

    @Composable
    override fun SearchPane(modifier: Modifier) =
        NeumorphismSearchPane(modifier = modifier.then(commonModifier))
}