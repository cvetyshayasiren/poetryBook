package com.cvetyshayasiren.poetrybook.domain

data class Poet(
    val id: Int,
    val lastPoemId: Int,
    val name: String,
    val poems: Poems
)

typealias Poets = List<Poet>

fun Poets.getPoem(bookmark: PoemBookmark): Poem = this[bookmark.poetId()].poems[bookmark.poemId()]
