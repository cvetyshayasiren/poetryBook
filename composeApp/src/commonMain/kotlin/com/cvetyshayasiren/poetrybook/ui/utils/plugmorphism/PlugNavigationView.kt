package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destination
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStore
import com.cvetyshayasiren.poetrybook.ui.store.NavigationStoreIntent
import org.kodein.di.instance
import org.kodein.di.newInstance

@Composable
fun PlugNavigationView(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    style: IsmStyle,
) {
    val navigationStore: NavigationStore by di.instance()
    val navList = Destination.navList(isExpanded)
    Row(
        modifier = modifier
    ) {
        navList.forEach {
            Button(
                onClick = {
                    navigationStore.sendIntent(NavigationStoreIntent.NavigateTo(it))
                }
            ) {
                Text(it::class.simpleName ?: "???")
            }
        }
    }
}