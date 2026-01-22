package com.cvetyshayasiren.poetrybook.domain.models.bookmark

interface PoetBookmark {
    val id: Int

    fun toBasicPoetBookmark(): Basic = Basic(id = id)

    data class Basic(override val id: Int): PoetBookmark
}

typealias PoetBookmarks = List<PoetBookmark>