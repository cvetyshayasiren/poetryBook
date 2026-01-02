package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.poem.DatedPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.DatedPoemBookmarks
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class DataDatedPoemBookmark(
    val poetId: Int,
    val poemId: Int,
    val dateTime: LocalDateTime
) {
    fun toDatedPoemBookmark(): DatedPoemBookmark = DatedPoemBookmark(
        poetId = poetId,
        poemId = poemId,
        dateTime = dateTime
    )

    companion object {
        fun fromDatedPoemBookmark(bookmark: DatedPoemBookmark): DataDatedPoemBookmark = DataDatedPoemBookmark(
            poetId = bookmark.poetId,
            poemId = bookmark.poemId,
            dateTime = bookmark.dateTime
        )
    }
}

typealias DataDatedPoemBookmarks = List<DataDatedPoemBookmark>

fun DataDatedPoemBookmarks.toDatedPoemBookmarks(): DatedPoemBookmarks = map { it.toDatedPoemBookmark() }

fun fromDatedPoetBookmarks(bookmarks: DatedPoemBookmarks): DataDatedPoemBookmarks =
    bookmarks.map { DataDatedPoemBookmark.fromDatedPoemBookmark(it) }