package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.models.style.random
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.store.StyleStoreIntent
import org.kodein.di.instance

@Composable
fun PlugSettingsPane(modifier: Modifier, style: IsmStyle) {

    val styleStore: StyleStore by di.instance()
    val colors = remember { listOf(Color.Blue, Color.Green, Color.Red, Color.Yellow, Color.Cyan, Color.Magenta) }

    PlugCommonPane(modifier, style, "SETTINGS") {
        FlowRow {
            colors.forEach { color ->
                Button(
                    onClick = {
                        styleStore.sendIntent(StyleStoreIntent.SetSeedColor(seedColor = color))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color
                    )
                ) {
                    Text("!")
                }
            }
            Button(
                onClick = {
                    styleStore.sendIntent(StyleStoreIntent.SetSeedColor(seedColor = Color.random()))
                }
            ) {
                Text("?")
            }
        }
    }
}