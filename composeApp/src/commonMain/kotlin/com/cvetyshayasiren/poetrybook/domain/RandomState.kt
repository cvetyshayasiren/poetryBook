package com.cvetyshayasiren.poetrybook.domain

data class RandomState(
    val nextPoemBehaviour: NextPoemBehaviour,
    val isRandomiseSeed: Boolean,
    val isRandomiseIsm: Boolean,
    val isRandomiseThemeMode: Boolean,
)