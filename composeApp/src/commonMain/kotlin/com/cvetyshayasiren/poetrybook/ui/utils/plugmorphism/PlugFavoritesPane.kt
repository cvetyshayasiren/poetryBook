package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStore
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStoreEffect
import com.cvetyshayasiren.poetrybook.ui.store.FavoritesStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.utils.containers.AlertConfirmationDialog
import kotlinx.coroutines.flow.collect
import org.kodein.di.instance

@Composable
fun PlugFavoritesPane(modifier: Modifier, style: IsmStyle) {
    val favoritesStore: FavoritesStore by di.instance()
    val state = favoritesStore.state.collectAsState()
    val effect = favoritesStore.effect.collectAsStateWithLifecycle(initialValue = null)

    PlugCommonPane(modifier.fillMaxSize().padding(horizontal = 24.dp), style, "FAVORITES") {

        Button(
            onClick = { favoritesStore.sendIntent(FavoritesStoreIntent.ClearFavorites) }
        ) {
            Text("clear all")
        }

        LazyColumn {
            state.value.value.forEach { (poetBookmark, bookmarks) ->
                stickyHeader {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val favoriteAlpha by animateFloatAsState(if(poetBookmark.isInFavorites) 1f else .2f)
                        IconButton(
                            onClick = {
                                favoritesStore.sendIntent(FavoritesStoreIntent.ApplySwitchPoet(poetBookmark))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.error.copy(alpha = favoriteAlpha)
                            )
                        }
                        Text(
                            text = poetBookmark.name,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                items(items = bookmarks.toList()) { titledPoemBookmark ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = titledPoemBookmark.title
                        )
                        IconButton(
                            onClick = {
                                favoritesStore.sendIntent(FavoritesStoreIntent.DeletePoem(titledPoemBookmark))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete ${titledPoemBookmark.title} from favorites"
                            )
                        }
                    }
                }
            }
        }
    }

    AlertConfirmationDialog<FavoritesStoreEffect.ShowAddConfirmation>(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        effect = effect.value,
    ) { eff, enabled ->
        Column {
            Text("add ${eff.count}?")
            Button(
                onClick = {
                    favoritesStore.sendIntent(FavoritesStoreIntent.AddPoet(eff.poetBookmark))
                    enabled.value = false
                }
            ) {
                Text("ADD")
            }
        }
    }

    AlertConfirmationDialog<FavoritesStoreEffect.ShowDeleteConfirmation>(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.errorContainer),
        effect = effect.value,
    ) { eff, enabled ->
        Column {
            Text("delete ${eff.count}?")
            Button(
                onClick = {
                    favoritesStore.sendIntent(FavoritesStoreIntent.DeletePoet(eff.poetBookmark))
                    enabled.value = false
                }
            ) {
                Text("DELETE")
            }
        }
    }
}