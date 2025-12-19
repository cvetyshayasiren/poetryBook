package com.cvetyshayasiren.poetrybook.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.cvetyshayasiren.poetrybook.domain.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.StyleState
import com.cvetyshayasiren.poetrybook.domain.ThemeMode
import kotlinx.serialization.Serializable

@Serializable
data class DataStyleState(
    val ismStyle: IsmStyle,
    val seedColor: Int,
    val themeMode: ThemeMode
) {
    companion object {
        fun fromStyleState(state: StyleState): DataStyleState = DataStyleState(
            ismStyle = state.ismStyle,
            seedColor = state.seedColor.toArgb(),
            themeMode = state.themeMode
        )
    }
}

fun DataStyleState.toStyleState(): StyleState = StyleState(
    ismStyle = this.ismStyle,
    seedColor = Color(this.seedColor),
    themeMode = this.themeMode
)
