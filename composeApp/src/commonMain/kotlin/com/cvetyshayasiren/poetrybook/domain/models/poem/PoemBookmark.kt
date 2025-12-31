package com.cvetyshayasiren.poetrybook.domain.models.poem

import kotlin.jvm.JvmInline

@JvmInline
value class PoemBookmark(val value: Pair<Int, Int>) {
    fun poetId() = value.first
    fun poemId() = value.second

    companion object {
        fun fromIds(poetId: Int, poemId: Int): PoemBookmark = PoemBookmark(value = Pair(poetId, poemId))
    }
}