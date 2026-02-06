package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onLayoutRectChanged
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.BookSequenceSorting
import com.cvetyshayasiren.poetrybook.ui.store.SearchResultBookmark
import com.cvetyshayasiren.poetrybook.ui.store.SearchStore
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreEffect
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.SearchStoreState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance

@Composable
fun PlugSearchPane(modifier: Modifier, style: IsmStyle) {
    val searchStore: SearchStore by di.instance()
    val state = searchStore.state.collectAsState()
    val searchCompleteEffect = remember { mutableStateOf<SearchStoreEffect.HardSearchComplete?>(null) }
    val userInput = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        searchStore.effect.collect { effect ->
            if(effect is SearchStoreEffect.HardSearchComplete) {
                println("SNACK")
                searchCompleteEffect.value = effect
                delay(2000)
                searchCompleteEffect.value = null
            }
        }
    }

    if(searchCompleteEffect.value != null) {
        Snackbar(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Text("search res ${searchCompleteEffect.value}")
        }
    }

    PlugCommonPane(modifier.fillMaxSize().padding(horizontal = 24.dp), style, "SEARCH") {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput.value,
            onValueChange = {
                scope.launch {
                    userInput.value = it
                    searchStore.sendIntent(SearchStoreIntent.SimpleSearchUserInput(userInput.value))
                }
            }
        )

        AnimatedContent(targetState = state.value) { state ->
            Column {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start)
                ) {
                    if(state is SearchStoreState.Book) {
                        IconButton(
                            onClick = { searchStore.sendIntent(SearchStoreIntent.SwitchSorting) }
                        ) {
                            Icon(
                                imageVector = when(state.sorting) {
                                    BookSequenceSorting.Alphabet -> Icons.Outlined.SortByAlpha
                                    BookSequenceSorting.Favorite -> Icons.Outlined.FavoriteBorder
                                },
                                contentDescription = "sort book"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainer),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if(userInput.value.isNotEmpty()) {
                            Button(
                                onClick = {
                                    searchStore.sendIntent(SearchStoreIntent.HardSearchUserInput(userInput.value))
                                }
                            ) {
                                Text("искать")
                            }
                        }
                    }
                }

                when(state) {
                    is SearchStoreState.Book -> {
                        val lazyListState = rememberLazyListState()
                        val index = state.selectedPoetIndex()
                        val letter = remember { mutableStateOf(".") }

                        LaunchedEffect(Unit) {
                            if(index != null) { lazyListState.animateScrollToItem(index * 2) }
                        }

                        val expandableList = remember {
                            mutableStateListOf(
                                *List(state.book.value.size) { it == index }.toTypedArray()
                            )
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            state = lazyListState
                        ) {
                            state.book.value.forEach { (poetBookmark, bookmarks) ->
                                val currentIndex = state.book.value.keys.indexOf(poetBookmark)
                                stickyHeader(key = poetBookmark) {
                                    val isActive = remember { mutableStateOf(false) }
                                    Row(
                                        modifier = Modifier
                                            .onLayoutRectChanged { layout ->
                                                isActive.value = layout.boundsInRoot.top == 152
                                            }
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                            .padding(12.dp)
                                            .clickable {
                                                expandableList[currentIndex] = !expandableList[currentIndex]
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(.7f).basicMarquee(),
                                            text = poetBookmark.name,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(text = "(${bookmarks.size})")
                                        if(isActive.value) { Text(text = "[${letter.value}]") }
                                        Text("${isActive.value}")
                                    }
                                }

                                item {
                                    if(expandableList[currentIndex]) {
                                        Column(
                                            modifier = Modifier.animateContentSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            bookmarks.forEach { titledBookBookmark ->
                                                Row(
                                                    modifier = Modifier
                                                        .onVisibilityChanged { visible ->
                                                            if(visible) {
                                                                letter.value = titledBookBookmark.title.first().uppercase()
                                                            }
                                                        }
                                                        .fillMaxWidth()
                                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                                        .padding(12.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = titledBookBookmark.title,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    if(titledBookBookmark.isInFavorites) {
                                                        Icon(
                                                            imageVector = Icons.Default.Favorite,
                                                            contentDescription = "in favorites",
                                                            tint = MaterialTheme.colorScheme.errorContainer
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is SearchStoreState.HardSearchResult -> {
                        val hardResults = remember { mutableStateListOf<SearchResultBookmark>() }

                        LaunchedEffect(Unit) {
                            state.searchResult.collect { result -> hardResults.add(result) }
                        }

                        LazyColumn {
                            items(hardResults) { bookmark ->
                                Text(
                                    text = bookmark.poetName.text,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = bookmark.title.text,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                    is SearchStoreState.SimpleSearchResult -> {
                        LazyColumn {
                            items(state.searchResult) { bookmark ->
                                Text(
                                    text = bookmark.poetName.text,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = bookmark.title.text,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}