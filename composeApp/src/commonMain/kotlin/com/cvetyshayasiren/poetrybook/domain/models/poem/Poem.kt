package com.cvetyshayasiren.poetrybook.domain.models.poem

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark

data class Poem(
    val id: Int,
    val poetId: Int,
    val poetName: String,
    val title: String,
    val text: String
)

fun Poem.toBasicPoemBookmark() = Bookmark.Basic(poetId = poetId, poemId = id)