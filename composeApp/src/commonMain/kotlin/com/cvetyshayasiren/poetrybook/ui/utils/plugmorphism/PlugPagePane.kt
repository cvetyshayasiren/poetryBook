package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.store.PageStore
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.PageStoreState
import org.kodein.di.instance

@Composable
fun PlugPagePane(modifier: Modifier, style: IsmStyle) {
    val pageStore: PageStore by di.instance()
    val state = pageStore.state.collectAsState()
    PlugCommonPane(modifier, style, "PAGE") {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = state.value
        ) {pageState ->
            when(pageState) {
                PageStoreState.Loading -> Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(250.dp)) }
                is PageStoreState.Prepared -> {
                    Column(
                        modifier = Modifier
                            .padding(48.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = pageState.poem.poetName,
                            fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(pageState.poem.title)
                        HorizontalDivider()
                        Text(pageState.poem.text)
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
                            Spacer(modifier = Modifier.height(96.dp))
                        }
                    }
                }
            }
        }
    }
}