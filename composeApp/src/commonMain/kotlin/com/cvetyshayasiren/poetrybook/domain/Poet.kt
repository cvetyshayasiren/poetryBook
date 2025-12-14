package com.cvetyshayasiren.poetrybook.domain

data class Poet(
    val id: Int,
    val lastPoemId: Int,
    val name: String,
    val poems: Poems
)
