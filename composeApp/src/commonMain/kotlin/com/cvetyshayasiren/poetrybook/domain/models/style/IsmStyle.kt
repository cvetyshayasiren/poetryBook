package com.cvetyshayasiren.poetrybook.domain.models.style

import com.cvetyshayasiren.poetrybook.domain.utils.PrettyLabel
import com.cvetyshayasiren.poetrybook.domain.utils.random

enum class IsmStyle(val label: PrettyLabel) {
    NEU(PrettyLabel("neumorphism", "неоморфизм")),
    BRUT(PrettyLabel("brutalism", "брутализм")),
    BAU(PrettyLabel("bauhaus", "баухаус")),
    GLASS(PrettyLabel("glassmorphism", "глассморфизм"));

    companion object {
        fun random(exclude: IsmStyle? = null): IsmStyle = entries.random(exclude = exclude)
    }
}