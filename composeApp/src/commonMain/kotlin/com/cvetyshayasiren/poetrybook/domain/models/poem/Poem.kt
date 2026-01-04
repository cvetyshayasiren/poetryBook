package com.cvetyshayasiren.poetrybook.domain.models.poem

data class Poem(
    val id: Int,
    val poetId: Int,
    val poetName: String,
    val title: String,
    val text: String
) {
    fun toBasicPoemBookmark(): BasicPoemBookmark = BasicPoemBookmark(poetId = poetId, poemId = id)
    fun toTitledPoemBookmark(): TitledPoemBookmark = TitledPoemBookmark(
        poetId = poetId,
        poemId = id,
        poetName = poetName,
        title = title
    )
}