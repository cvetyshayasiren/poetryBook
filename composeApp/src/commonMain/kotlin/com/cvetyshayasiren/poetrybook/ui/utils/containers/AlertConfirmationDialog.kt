package com.cvetyshayasiren.poetrybook.ui.utils.containers

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun<reified E> AlertConfirmationDialog(
    modifier: Modifier = Modifier,
    effect: Any?,
    crossinline content: @Composable (E, MutableState<Boolean>) -> Unit,
) {
    val enabled = remember { mutableStateOf(false) }
    if(effect !is E) { return }

    LaunchedEffect(effect) {
        enabled.value = true
    }

    if(enabled.value) {
        BasicAlertDialog(
            onDismissRequest = { enabled.value = false },
            modifier = modifier,
            properties = DialogProperties(),
            content = { content(effect, enabled) }
        )
    }
}