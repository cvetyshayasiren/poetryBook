package com.cvetyshayasiren.poetrybook.domain.models.poem

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline
import kotlin.time.TimeMark

interface PoemBookmark {
    val poetId: Int
    val poemId: Int
}

data class BasicPoemBookmark(override val poetId: Int, override val poemId: Int): PoemBookmark

data class TitledPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val poetName: String,
    val poemName: String
): PoemBookmark

data class DatedPoemBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val dateTime: LocalDateTime
): PoemBookmark

typealias BasicPoemBookmarks = List<BasicPoemBookmark>
typealias TitledPoemBookmarks = List<TitledPoemBookmark>
typealias DatedPoemBookmarks = List<DatedPoemBookmark>