package com.cvetyshayasiren.poetrybook.domain.models.random

data class RandomState(
    val randomPoemBehaviour: RandomPoemBehaviour = RandomPoemBehaviour.RandomPoet,
    val isRandomiseSeed: Boolean = false,
    val isRandomiseIsm: Boolean = false,
    val isRandomiseThemeMode: Boolean = false,
    val isRandomisePaletteStyle: Boolean = false,
    val isRandomiseColorSpecVersion: Boolean = false,
) {
    fun isNeedStyleChange(): Boolean = isRandomiseSeed || isRandomiseIsm ||
            isRandomiseThemeMode || isRandomisePaletteStyle || isRandomiseColorSpecVersion
}