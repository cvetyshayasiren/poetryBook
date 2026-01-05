package com.cvetyshayasiren.poetrybook.domain.models.poem

import com.cvetyshayasiren.poetrybook.domain.utils.currentTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface PoemBookmark {
    val poetId: Int
    val poemId: Int
}

data class BasicPoemBookmark(override val poetId: Int, override val poemId: Int): PoemBookmark

data class TitledPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val poetName: String,
    val title: String
): PoemBookmark

data class DatedPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val dateTime: LocalDateTime
): PoemBookmark

data class SearchResultPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val poetName: SearchResult,
    val title: SearchResult,
    val text: SearchResult? = null
): PoemBookmark {
    data class SearchResult(
        val text: String,
        val ranges: List<IntRange>
    )
}

typealias BasicPoemBookmarks = List<BasicPoemBookmark>
typealias TitledPoemBookmarks = List<TitledPoemBookmark>
typealias DatedPoemBookmarks = List<DatedPoemBookmark>
typealias SearchResultPoemBookmarks = List<SearchResultPoemBookmark>

fun PoemBookmark.toDatedPoemBookmark(): DatedPoemBookmark =
    DatedPoemBookmark(
        poetId = poetId,
        poemId = poemId,
        dateTime = currentTime()
    )