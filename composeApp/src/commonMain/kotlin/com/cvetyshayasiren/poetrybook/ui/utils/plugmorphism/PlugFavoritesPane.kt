package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStore
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PoetryBookStore
import org.kodein.di.instance

@Composable
fun PlugFavoritesPane(modifier: Modifier, style: IsmStyle) {
    val favoritesStore: FavoritesStore by di.instance()
    val state = favoritesStore.state.collectAsState()

    PlugCommonPane(modifier, style, "FAVORITES") {

        Button(
            onClick = { favoritesStore.sendIntent(FavoritesStoreIntent.ClearFavorites) }
        ) {
            Text("clear all")
        }

        LazyColumn {
            state.value.value.forEach { (poetBookmark, titledPoemBookmarks) ->
                stickyHeader {
                    Text(
                        text = poetBookmark.name,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                items(items = titledPoemBookmarks) { titledPoemBookmark ->
                    Text(
                        text = titledPoemBookmark.title
                    )
                }
            }
        }
    }
}