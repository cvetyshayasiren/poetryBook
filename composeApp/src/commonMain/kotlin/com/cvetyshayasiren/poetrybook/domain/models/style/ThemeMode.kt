package com.cvetyshayasiren.poetrybook.domain.models.style

enum class ThemeMode {
    DARK, LIGHT, SYSTEM;

    companion object {
        fun random(): ThemeMode = listOf(DARK, LIGHT).random()
    }
}