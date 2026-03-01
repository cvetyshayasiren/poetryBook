package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreState
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.instance
import poetrybook.composeapp.generated.resources.Res
import poetrybook.composeapp.generated.resources.compose_multiplatform

@Composable
fun PlugPagePane(modifier: Modifier, style: IsmStyle) {
    val pageStore: PageStore by di.instance()
    val state = pageStore.state.collectAsState()
    PlugCommonPane(modifier.fillMaxSize().padding(horizontal = 24.dp), style, "PAGE") {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            targetState = state.value,
            contentKey = { it ->
                when(it) {
                    PageStoreState.Loading -> { it.hashCode() }
                    is PageStoreState.Prepared -> { it.page.text }
                }
            }
        ) { pageState ->
            when(pageState) {
                PageStoreState.Loading -> Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(250.dp)) }
                is PageStoreState.Prepared -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Image(
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = "lal"
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.clickable {
                                    pageStore.sendIntent(
                                        PageStoreIntent.NavigateToSearch(
                                            pageState.page.toBasicPoetBookmark()
                                        )
                                    )

                                },
                                text = pageState.page.poetName,
                                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                                textDecoration = TextDecoration.Underline
                            )

                            IconButton(
                                onClick = {
                                    pageStore.sendIntent(PageStoreIntent.NavigateToSearch())
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val favoriteAlpha by animateFloatAsState(if(pageState.page.isInFavorites) 1f else .2f)
                            IconButton(
                                onClick = {
                                    pageStore.sendIntent(PageStoreIntent.SwitchFavorites)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = favoriteAlpha)
                                )
                            }
                            Text(pageState.page.title)
                        }

                        HorizontalDivider()
                        Text(pageState.page.text)
                        Row(
                            modifier = Modifier.fillMaxWidth(.5f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    pageStore.sendIntent(PageStoreIntent.SwitchPrevious)
                                }
                            ) {
                                Text("<")
                            }
                            Button(
                                onClick = {
                                    pageStore.sendIntent(PageStoreIntent.SwitchNext)
                                }
                            ) {
                                Text(">")
                            }
                            Spacer(modifier = Modifier.height(400.dp))
                        }
                    }
                }
            }
        }
    }
}