package com.cvetyshayasiren.poetrybook.domain.models.random

data class RandomState(
    val randomPoemBehaviour: RandomPoemBehaviour = RandomPoemBehaviour.RANDOM_POET,
    val isRandomiseSeed: Boolean = false,
    val isRandomiseIsm: Boolean = false,
    val isRandomiseThemeMode: Boolean = false,
)