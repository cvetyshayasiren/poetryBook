package com.cvetyshayasiren.poetrybook.ui.utils.plugmorphism

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.models.style.ThemeMode
import com.cvetyshayasiren.poetrybook.domain.models.style.random
import com.cvetyshayasiren.poetrybook.ui.store.*
import com.cvetyshayasiren.poetrybook.ui.utils.containers.AlertConfirmationDialog
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import org.kodein.di.instance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlugSettingsPane(modifier: Modifier, style: IsmStyle) {

    val styleStore: StyleStore by di.instance()
    val randomStore: RandomStore by di.instance()
    val settingsStore: SettingsStore by di.instance()
    val styleState = styleStore.state.collectAsState()
    val randomState = randomStore.state.collectAsState()
    val settingsEffect = settingsStore.effect.collectAsState(null)

    val colors = remember { listOf(Color.Green, Color.Red, Color.Yellow) }

    PlugCommonPane(modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)
        .verticalScroll(rememberScrollState()), style, "SETTINGS"
    ) {

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
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {
            PaletteStyle.entries.forEach { paletteStyle ->
                val borderColor by animateColorAsState(if(styleState.value.paletteStyle == paletteStyle)
                    MaterialTheme.colorScheme.tertiary else Color.Transparent
                )
                Button(
                    border = BorderStroke(width = 4.dp, color = borderColor),
                    onClick = {
                        styleStore.sendIntent(StyleStoreIntent.SetPaletteStyle(paletteStyle = paletteStyle))
                    },
                ) {
                    Text(paletteStyle.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {
            ColorSpec.SpecVersion.entries.forEach { colorSpecVersion ->
                val borderColor by animateColorAsState(if(styleState.value.colorSpecVersion == colorSpecVersion)
                    MaterialTheme.colorScheme.tertiary else Color.Transparent
                )
                Button(
                    border = BorderStroke(width = 4.dp, color = borderColor),
                    onClick = {
                        styleStore.sendIntent(StyleStoreIntent.SetColorSpecVersion(colorSpecVersion = colorSpecVersion))
                    },
                ) {
                    Text(colorSpecVersion.name)
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
            val expandedPoemBehaviourMenu = remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedPoemBehaviourMenu.value,
                onExpandedChange = { expandedPoemBehaviourMenu.value = !expandedPoemBehaviourMenu.value }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                    readOnly = true,
                    value = randomState.value.randomState.randomPoemBehaviour.prettyName(),
                    onValueChange = { },
                    label = { Text("поведение случайности") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPoemBehaviourMenu.value) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedPoemBehaviourMenu.value,
                    onDismissRequest = { expandedPoemBehaviourMenu.value = false }
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.border(
                            width = 4.dp, color = (if(randomState.value.randomState.randomPoemBehaviour is RandomPoemBehaviour.RandomPoet)
                                MaterialTheme.colorScheme.tertiary else Color.Transparent)
                        ),
                        onClick = {
                            expandedPoemBehaviourMenu.value = !expandedPoemBehaviourMenu.value
                            randomStore.sendIntent(RandomStoreIntent.SetNextPoemBehaviour(RandomPoemBehaviour.RandomPoet))
                        },
                        text = { Text("случайно") }
                    )

                    if(randomState.value.favoritesIsNotEmpty()) {
                        DropdownMenuItem(
                            modifier = Modifier.border(
                                width = 4.dp, color = (if(randomState.value.randomState.randomPoemBehaviour is RandomPoemBehaviour.FromFavorites)
                                    MaterialTheme.colorScheme.tertiary else Color.Transparent)
                            ),
                            onClick = {
                                expandedPoemBehaviourMenu.value = !expandedPoemBehaviourMenu.value
                                randomStore.sendIntent(RandomStoreIntent.SetNextPoemBehaviour(RandomPoemBehaviour.FromFavorites))
                            },
                            text = { Text("избранное") }
                        )
                    }

                    DropdownMenuItem(
                        modifier = Modifier.border(
                            width = 4.dp, color = (if(randomState.value.randomState.randomPoemBehaviour is RandomPoemBehaviour.SamePoet)
                                MaterialTheme.colorScheme.tertiary else Color.Transparent)
                        ),
                        onClick = {
                            expandedPoemBehaviourMenu.value = !expandedPoemBehaviourMenu.value
                            randomStore.sendIntent(RandomStoreIntent.SetNextPoemBehaviour(RandomPoemBehaviour.SamePoet))
                        },
                        text = { Text("повторять") }
                    )
                    DropdownMenuItem(
                        modifier = Modifier.border(
                            width = 4.dp, color = (if(randomState.value.randomState.randomPoemBehaviour is RandomPoemBehaviour.CertainPoet)
                                MaterialTheme.colorScheme.tertiary else Color.Transparent)
                        ),
                        onClick = {
                            expandedPoemBehaviourMenu.value = !expandedPoemBehaviourMenu.value
                            randomStore.sendIntent(
                                RandomStoreIntent.SetNextPoemBehaviour(
                                    RandomPoemBehaviour.CertainPoet(
                                        poetBookmark = randomState.value.poetsBundle.first()
                                    )
                                )
                            )
                        },
                        text = { Text("выбрать") }
                    )
                }
            }

            AnimatedVisibility(
                visible = randomState.value.randomState.randomPoemBehaviour is RandomPoemBehaviour.CertainPoet
            ) {
                val expandedCertainPoetMenu = remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedCertainPoetMenu.value,
                    onExpandedChange = { expandedCertainPoetMenu.value = !expandedCertainPoetMenu.value }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                        readOnly = true,
                        value = randomState.value.poetsBundle.option(),
                        onValueChange = { },
                        label = { Text("поэт") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPoemBehaviourMenu.value) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCertainPoetMenu.value,
                        onDismissRequest = { expandedCertainPoetMenu.value = false }
                    ) {
                        randomState.value.poetsBundle.fullList().forEach { poetBookmark ->
                            DropdownMenuItem(
                                onClick = {
                                    expandedCertainPoetMenu.value = !expandedCertainPoetMenu.value
                                    randomStore.sendIntent(
                                        RandomStoreIntent.SetNextPoemBehaviour(
                                            RandomPoemBehaviour.CertainPoet(poetBookmark = poetBookmark)
                                        )
                                    )
                                },
                                text = { Text(poetBookmark.name) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = when(poetBookmark.type) {
                                            BundleBookmark.Type.OPTION -> Icons.Rounded.Check
                                            BundleBookmark.Type.CURRENT -> Icons.Rounded.People
                                            BundleBookmark.Type.FAVORITE -> Icons.Rounded.Favorite
                                            BundleBookmark.Type.HISTORY -> Icons.Rounded.History
                                            BundleBookmark.Type.OTHER -> Icons.Rounded.SortByAlpha
                                        },
                                        contentDescription = "set ${poetBookmark.name}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.randomState.isRandomiseIsm,
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
                checked = randomState.value.randomState.isRandomiseSeed,
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
                checked = randomState.value.randomState.isRandomiseThemeMode,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomiseThemeMod(it))
                }
            )
            Text("IsRandomiseThemeMode")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.randomState.isRandomisePaletteStyle,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomisePaletteStyle(it))
                }
            )
            Text("IsRandomisePaletteStyle")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)

        ) {
            Switch(
                checked = randomState.value.randomState.isRandomiseColorSpecVersion,
                onCheckedChange = {
                    randomStore.sendIntent(RandomStoreIntent.SetIsRandomiseColorSpecVersion(it))
                }
            )
            Text("IsRandomiseColorSpecVersion")
        }

        Spacer(modifier = Modifier.height(48.dp))
        HorizontalDivider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    settingsStore.sendIntent(SettingsStoreIntent.SetDefault)
                }
            ) {
                Text("DEFAULT")
            }
            Button(
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                onClick = {
                    settingsStore.sendIntent(SettingsStoreIntent.ApplyWipe)
                }
            ) {
                Text("WIPE")
            }
        }

        Spacer(modifier = Modifier.height(240.dp))

        AlertConfirmationDialog<SettingsStoreEffect.ShowWipeConfirmation>(
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.surfaceContainer),
            effect = settingsEffect.value,
        ) { _, enabled ->
            Column {
                Text("WIPE all?")
                Button(
                    onClick = {
                        settingsStore.sendIntent(SettingsStoreIntent.Wipe)
                        enabled.value = false
                    }
                ) {
                    Text("wipe")
                }
            }
        }
    }
}