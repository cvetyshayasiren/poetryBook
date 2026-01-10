package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStore
import com.cvetyshayasiren.poetrybook.ui.store.PoetryBookStore
import org.kodein.di.instance

@Composable
fun PlugFavoritesPane(modifier: Modifier, style: IsmStyle) {
    val favoritesStore: FavoritesStore by di.instance()
    val state = favoritesStore.state.collectAsState()
    val poetryBookStore: PoetryBookStore by di.instance()
    PlugCommonPane(modifier, style, "FAVORITES") {

    }
}