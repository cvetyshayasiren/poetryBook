package com.cvetyshayasiren.poetrybook.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.cvetyshayasiren.poetrybook.domain.models.style.IsmStyle
import com.cvetyshayasiren.poetrybook.domain.models.style.StyleState
import com.cvetyshayasiren.poetrybook.domain.models.style.ThemeMode
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import kotlinx.serialization.Serializable

@Serializable
data class DataStyleState(
    val ismStyle: DataIsmStyle,
    val seedColor: Int,
    val themeMode: DataThemeMode,
    val paletteStyle: DataPaletteStyle,
    val colorSpecVersion: DataColorSpecVersion,
) {
    fun toStyleState(): StyleState = StyleState(
        ismStyle = this.ismStyle.toIsmStyle(),
        seedColor = Color(this.seedColor),
        themeMode = this.themeMode.toThemeMode(),
        paletteStyle = this.paletteStyle.toPaletteStyle(),
        colorSpecVersion = this.colorSpecVersion.toColorSpecVersion(),
    )
    companion object {
        fun fromStyleState(state: StyleState): DataStyleState = DataStyleState(
            ismStyle = DataIsmStyle.fromIsmStyle(state.ismStyle),
            seedColor = state.seedColor.toArgb(),
            themeMode = DataThemeMode.fromThemeMode(state.themeMode),
            paletteStyle = DataPaletteStyle.fromPaletteStyle(state.paletteStyle),
            colorSpecVersion = DataColorSpecVersion.fromColorSpecVersion(state.colorSpecVersion)
        )
    }
}

enum class DataIsmStyle {
    NEU, BRUT, BAU, GLASS;

    fun toIsmStyle(): IsmStyle = when(this) {
        NEU -> IsmStyle.NEU
        BRUT -> IsmStyle.BRUT
        BAU -> IsmStyle.BAU
        GLASS -> IsmStyle.GLASS
    }

    companion object {
        fun fromIsmStyle(style: IsmStyle): DataIsmStyle = when(style) {
            IsmStyle.NEU -> NEU
            IsmStyle.BRUT -> BRUT
            IsmStyle.BAU -> BAU
            IsmStyle.GLASS -> GLASS
        }
    }
}

enum class DataThemeMode {
    DARK, LIGHT, SYSTEM;

    fun toThemeMode(): ThemeMode = when(this) {
        DARK -> ThemeMode.DARK
        LIGHT -> ThemeMode.LIGHT
        SYSTEM -> ThemeMode.SYSTEM
    }

    companion object {
        fun fromThemeMode(mode: ThemeMode): DataThemeMode = when(mode) {
            ThemeMode.DARK -> DARK
            ThemeMode.LIGHT -> LIGHT
            ThemeMode.SYSTEM -> SYSTEM
        }
    }
}

enum class DataPaletteStyle {
    TonalSpot, Neutral, Vibrant, Expressive,
    Rainbow, FruitSalad, Monochrome, Fidelity,
    Content;

    fun toPaletteStyle(): PaletteStyle = when(this) {
        TonalSpot -> PaletteStyle.TonalSpot
        Neutral -> PaletteStyle.Neutral
        Vibrant -> PaletteStyle.Vibrant
        Expressive -> PaletteStyle.Expressive
        Rainbow -> PaletteStyle.Rainbow
        FruitSalad -> PaletteStyle.FruitSalad
        Monochrome -> PaletteStyle.Monochrome
        Fidelity -> PaletteStyle.Fidelity
        Content -> PaletteStyle.Content
    }

    companion object {
        fun fromPaletteStyle(paletteStyle: PaletteStyle): DataPaletteStyle = when(paletteStyle) {
            PaletteStyle.TonalSpot -> TonalSpot
            PaletteStyle.Neutral -> Neutral
            PaletteStyle.Vibrant -> Vibrant
            PaletteStyle.Expressive -> Expressive
            PaletteStyle.Rainbow -> Rainbow
            PaletteStyle.FruitSalad -> FruitSalad
            PaletteStyle.Monochrome -> Monochrome
            PaletteStyle.Fidelity -> Fidelity
            PaletteStyle.Content -> Content
        }
    }
}

enum class DataColorSpecVersion {
    SPEC_2021, SPEC_2025;

    fun toColorSpecVersion(): ColorSpec.SpecVersion = when(this) {
        SPEC_2021 -> ColorSpec.SpecVersion.SPEC_2021
        SPEC_2025 -> ColorSpec.SpecVersion.SPEC_2025
    }

    companion object {
        fun fromColorSpecVersion(colorSpecVersion: ColorSpec.SpecVersion): DataColorSpecVersion = when(colorSpecVersion) {
            ColorSpec.SpecVersion.SPEC_2021 -> SPEC_2021
            ColorSpec.SpecVersion.SPEC_2025 -> SPEC_2025
        }
    }
}
