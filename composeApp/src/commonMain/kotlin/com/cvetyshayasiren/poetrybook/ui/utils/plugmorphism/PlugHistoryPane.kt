package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.utils.prettyTimeString
import com.cvetyshayasiren.poetrybook.ui.store.HistoryStore
import com.cvetyshayasiren.poetrybook.ui.store.HistoryStoreIntent
import org.kodein.di.instance

@Composable
fun PlugHistoryPane(modifier: Modifier, style: IsmStyle) {
    val historyStore: HistoryStore by di.instance()
    val state = historyStore.state.collectAsState()

    PlugCommonPane(modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)
        .verticalScroll(rememberScrollState()), style, "HISTORY"
    ) {
        if(state.value.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        historyStore.sendIntent(HistoryStoreIntent.ClearAll)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "delete all from history"
                    )
                }
                Text("clear all")
            }
        }
        
        state.value.forEachIndexed { index, bookmark ->
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable {
                        historyStore.sendIntent(HistoryStoreIntent.NavigateAndSwitch(bookmark))
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = bookmark.poetName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(bookmark.title, color = MaterialTheme.colorScheme.onSurface)
                    Text(bookmark.dateTime.prettyTimeString(), fontWeight = FontWeight.Thin, color = MaterialTheme.colorScheme.onSurface)
                }
                IconButton(
                    onClick = {
                        historyStore.sendIntent(HistoryStoreIntent.Clear(index))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "delete from history"
                    )
                }
            }
        }
    }
}