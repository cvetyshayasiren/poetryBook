package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.ui.navigation.Destinations

@Composable
fun PlugNavigationView(
    backStack: SnapshotStateList<Destinations>,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    style: IsmStyle,
) {
    val navList = Destinations.navList(isExpanded)
    Row(
        modifier = modifier
    ) {
        navList.forEach {
            Button(
                onClick = {
                    backStack.add(it)
                }
            ) {
                Text(it::class.simpleName ?: "???")
            }
        }
    }
}