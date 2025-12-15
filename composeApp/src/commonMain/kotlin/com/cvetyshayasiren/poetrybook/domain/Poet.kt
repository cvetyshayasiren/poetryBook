package com.cvetyshayasiren.poetrybook.domain

typealias Poets = List<Poet>

data class Poet(
    val id: Int,
    val lastPoemId: Int,
    val name: String,
    val poems: Poems
)
