package com.cvetyshayasiren.poetrybook.domain

import kotlin.jvm.JvmInline

data class Poem(
    val id: Int,
    val poetId: Int,
    val lastPoemId: Int,
    val poetName: String,
    val title: String,
    val text: String
) {
    fun nextPoemBookmark(): PoemBookmark =
        PoemBookmark.fromIds(poetId = poetId, poemId = (id + 1) % lastPoemId)

    fun previousPoemBookmark(): PoemBookmark =
        PoemBookmark.fromIds(poetId = poetId, poemId = (lastPoemId + id - 1) % lastPoemId)
}

typealias Poems = List<Poem>

@JvmInline
value class PoemBookmark(val value: Pair<Int, Int>) {
    fun poetId() = value.first
    fun poemId() = value.second

    companion object {
        fun fromIds(poetId: Int, poemId: Int): PoemBookmark = PoemBookmark(value = Pair(poetId, poemId))
    }
}