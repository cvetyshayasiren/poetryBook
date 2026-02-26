package com.cvetyshayasiren.poetrybook.ui.styles.brutalism.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BrutalismIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color = MaterialTheme.colorScheme.inversePrimary,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    content: @Composable (() -> Unit)
) {
    val interactionSource = remember { MutableInteractionSource() }
    IconButton(
        modifier = Modifier
            .brutalShadow(
                backgroundColor = buttonColor,
                shadowColor = shadowColor,
                borderColor = borderColor,
            )
            .then(modifier),
        interactionSource = interactionSource,
        onClick = onClick,
        content = content
    )
}