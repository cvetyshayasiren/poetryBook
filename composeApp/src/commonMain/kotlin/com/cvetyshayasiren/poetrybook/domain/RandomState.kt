package com.cvetyshayasiren.poetrybook.domain

import kotlin.random.Random

data class RandomState(
    val nextPoemBehaviour: NextPoemBehaviour = NextPoemBehaviour.RANDOM_POET,
    val isRandomiseSeed: Boolean = false,
    val isRandomiseIsm: Boolean = false,
    val isRandomiseThemeMode: Boolean = false,
)