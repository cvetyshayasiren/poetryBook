package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.models.style.ThemeMode
import com.cvetyshayasiren.poetrybook.domain.models.style.random
import com.cvetyshayasiren.poetrybook.ui.store.RandomStore
import com.cvetyshayasiren.poetrybook.ui.store.RandomStoreIntent
import com.cvetyshayasiren.poetrybook.ui.store.StyleStore
import com.cvetyshayasiren.poetrybook.ui.store.StyleStoreIntent
import org.kodein.di.instance

@Composable
fun PlugSettingsPane(modifier: Modifier, style: IsmStyle) {

    val styleStore: StyleStore by di.instance()
    val randomStore: RandomStore by di.instance()
    val styleState = styleStore.state.collectAsState()
    val randomState = randomStore.state.collectAsState()

    val colors = remember { listOf(Color.Green, Color.Red, Color.Yellow) }

    PlugCommonPane(modifier.verticalScroll(rememberScrollState()), style, "SETTINGS") {

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {

            IsmStyle.entries.forEach { ismStyle ->
                val borderColor by animateColorAsState(if(styleState.value.ismStyle == ismStyle)
                    MaterialTheme.colorScheme.tertiary else Color.Transparent
                )
                Button(
                    border = BorderStroke(width = 4.dp, color = borderColor),
                    onClick = {
                        styleStore.sendIntent(StyleStoreIntent.SetIsmStyle(ismStyle = ismStyle))
                    }
                ) {
                    Text(ismStyle.label.lowerRu())
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {
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
                border = BorderStroke(width = 4.dp, color = styleState.value.seedColor),
                onClick = {
                    styleStore.sendIntent(StyleStoreIntent.SetSeedColor(seedColor = Color.random()))
                }
            ) {
                Text("?")
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {
            ThemeMode.entries.forEach { themeMode ->
                val borderColor by animateColorAsState(if(styleState.value.themeMode == themeMode)
                    MaterialTheme.colorScheme.tertiary else Color.Transparent
                )
                Button(
                    border = BorderStroke(width = 4.dp, color = borderColor),
                    onClick = {
                        styleStore.sendIntent(StyleStoreIntent.SetThemeMode(themeMode = themeMode))
                    },
                ) {
                    Text(themeMode.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        HorizontalDivider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(48.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {
            Button(
                border = BorderStroke(
                    width = 4.dp, color = (if(randomState.value.randomPoemBehaviour is RandomPoemBehaviour.CertainPoet)
                                MaterialTheme.colorScheme.tertiary else Color.Transparent)),
                onClick = {
                    randomStore.sendIntent(RandomStoreIntent
                        .SetNextPoemBehaviour(RandomPoemBehaviour.CertainPoet(0)))
                },
            ) {
                Text("CERTAINT POET")
            }
            Button(
                border = BorderStroke(
                    width = 4.dp, color = (if(randomState.value.randomPoemBehaviour is RandomPoemBehaviour.FromFavorites)
                        MaterialTheme.colorScheme.tertiary else Color.Transparent)),
                onClick = {
                    randomStore.sendIntent(RandomStoreIntent
                        .SetNextPoemBehaviour(RandomPoemBehaviour.FromFavorites))
                },
            ) {
                Text("favorites")
            }
            Button(
                border = BorderStroke(
                    width = 4.dp, color = (if(randomState.value.randomPoemBehaviour is RandomPoemBehaviour.RandomPoet)
                        MaterialTheme.colorScheme.tertiary else Color.Transparent)),
                onClick = {
                    randomStore.sendIntent(RandomStoreIntent
                        .SetNextPoemBehaviour(RandomPoemBehaviour.RandomPoet))
                },
            ) {
                Text("random")
            }
            Button(
                border = BorderStroke(
                    width = 4.dp, color = (if(randomState.value.randomPoemBehaviour is RandomPoemBehaviour.SamePoet)
                        MaterialTheme.colorScheme.tertiary else Color.Transparent)),
                onClick = {
                    randomStore.sendIntent(RandomStoreIntent
                        .SetNextPoemBehaviour(RandomPoemBehaviour.SamePoet))
                },
            ) {
                Text("same")
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.isRandomiseIsm,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomiseIsm(it))
                }
            )
            Text("IsRandomiseIsm")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.isRandomiseSeed,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomiseSeed(it))
                }
            )
            Text("IsRandomiseSeed")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.isRandomiseThemeMode,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomiseThemeMod(it))
                }
            )
            Text("IsRandomiseThemeMode")
        }
        Spacer(modifier = Modifier.height(240.dp))
    }
}