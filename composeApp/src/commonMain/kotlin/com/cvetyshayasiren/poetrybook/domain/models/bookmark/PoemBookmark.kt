package com.cvetyshayasiren.poetrybook.domain.models.bookmark

import com.cvetyshayasiren.poetrybook.domain.utils.currentTime
import kotlinx.datetime.LocalDateTime

interface PoemBookmark {
    val poetId: Int
    val poemId: Int

    fun getPoetBookmark(): BasicPoetBookmark = BasicPoetBookmark(id = poetId)
}

typealias PoemBookmarks = List<PoemBookmark>

data class BasicPoemBookmark(override val poetId: Int, override val poemId: Int): PoemBookmark

data class DatedPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val dateTime: LocalDateTime
): PoemBookmark

fun PoemBookmark.toDatedPoemBookmark(): DatedPoemBookmark =
    DatedPoemBookmark(
        poetId = poetId,
        poemId = poemId,
        dateTime = currentTime()
    )

typealias DatedPoemBookmarks = List<DatedPoemBookmark>


