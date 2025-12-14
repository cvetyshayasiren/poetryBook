package com.cvetyshayasiren.poetrybook.domain

typealias Poems = List<Poem>

data class Poem(
    val id: Int,
    val poetId: Int,
    val lastPoemId: Int,
    val poetName: String,
    val title: String,
    val text: String
) {
    fun nextPoem(): Poem = TODO()

    fun previousPoem(): Poem = TODO()
}