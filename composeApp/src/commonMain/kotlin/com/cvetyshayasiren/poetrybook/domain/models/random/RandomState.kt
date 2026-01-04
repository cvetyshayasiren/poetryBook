package com.cvetyshayasiren.poetrybook.domain.models.random

data class RandomState(
    val randomPoemBehaviour: RandomPoemBehaviour = RandomPoemBehaviour.RandomPoet,
    val isRandomiseSeed: Boolean = false,
    val isRandomiseIsm: Boolean = false,
    val isRandomiseThemeMode: Boolean = false,
) {
    fun isNeedStyleChange(): Boolean =
    listOf(isRandomiseSeed, isRandomiseIsm, isRandomiseThemeMode).any()
}