package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class DataDatedBookmark(
    val poetId: Int,
    val poemId: Int,
    val dateTime: LocalDateTime
) {
    fun toDatedBookmark(): DatedBookmark = DatedBookmark(
        poetId = poetId,
        poemId = poemId,
        dateTime = dateTime
    )

    companion object {
        fun fromDatedBookmark(bookmark: DatedBookmark): DataDatedBookmark = DataDatedBookmark(
            poetId = bookmark.poetId,
            poemId = bookmark.poemId,
            dateTime = bookmark.dateTime
        )
    }
}

typealias DataDatedBookmarks = List<DataDatedBookmark>

fun DataDatedBookmarks.toDatedBookmarks(): DatedPoemBookmarks = map { it.toDatedBookmark() }

fun fromDatedPoetBookmarks(bookmarks: DatedPoemBookmarks): DataDatedBookmarks =
    bookmarks.map { DataDatedBookmark.fromDatedBookmark(it) }