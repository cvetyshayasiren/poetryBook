package com.cvetyshayasiren.poetrybook.domain.models.poem

data class Poem(
    val id: Int,
    val poetId: Int,
    val poetName: String,
    val title: String,
    val text: String
) {
//    fun nextPoemBookmark(): PoemBookmark =
//        PoemBookmark.fromIds(poetId = poetId, poemId = (id + 1) % lastPoemId)
//
//    fun previousPoemBookmark(): PoemBookmark =
//        PoemBookmark.fromIds(poetId = poetId, poemId = (lastPoemId + id - 1) % lastPoemId)
}