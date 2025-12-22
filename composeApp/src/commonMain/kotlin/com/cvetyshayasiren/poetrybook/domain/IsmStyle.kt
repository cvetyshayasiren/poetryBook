package com.cvetyshayasiren.poetrybook.domain

import com.cvetyshayasiren.poetrybook.domain.utils.PrettyLabel

enum class IsmStyle(val label: PrettyLabel) {
    NEU(PrettyLabel("neumorphism", "неоморфизм")),
    BRUT(PrettyLabel("brutalism", "брутализм")),
    BAU(PrettyLabel("bauhaus", "баухаус")),
    GLASS(PrettyLabel("glassmorphism", "глассморфизм"))
}