package com.cvetyshayasiren.poetrybook.domain.models.bookmark

import com.cvetyshayasiren.poetrybook.domain.utils.currentTime
import kotlinx.datetime.LocalDateTime

interface Bookmark {
    val poetId: Int
    val poemId: Int

    fun toBasicPoemBookmark(): Basic = Basic(poetId = poetId, poemId = poemId)

    data class Basic(override val poetId: Int, override val poemId: Int): Bookmark

    fun toBasicPoetBookmark(): PoetBookmark.Basic = PoetBookmark.Basic(id = poetId)
}

typealias Bookmarks = List<Bookmark>
typealias BasicBookmarks = List<Bookmark.Basic>


data class DatedBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val dateTime: LocalDateTime
): Bookmark

fun Bookmark.toDatedPoemBookmark(): DatedBookmark =
    DatedBookmark(
        poetId = poetId,
        poemId = poemId,
        dateTime = currentTime()
    )

typealias DatedPoemBookmarks = List<DatedBookmark>


