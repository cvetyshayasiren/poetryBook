package com.cvetyshayasiren.poetrybook.domain.models.bookmark

interface PoetBookmark {
    val id: Int
}

data class BasicPoetBookmark(override val id: Int): PoetBookmark

typealias PoetBookmarks = List<PoetBookmark>