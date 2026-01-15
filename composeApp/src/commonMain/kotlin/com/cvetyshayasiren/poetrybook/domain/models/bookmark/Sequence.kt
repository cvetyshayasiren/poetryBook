package com.cvetyshayasiren.poetrybook.domain.models.bookmark

import com.cvetyshayasiren.poetrybook.domain.utils.randomExcludeBy
import kotlin.jvm.JvmInline

@JvmInline
value class Sequence(val value: Map<PoetBookmark, PoemBookmarks>)

fun Sequence.random(exclude: PoemBookmark? = null): PoemBookmark =
    value.values.reduce { a, b -> a + b }.run {
        when(exclude == null) {
            true -> random()
            false -> randomExcludeBy { poemBookmark ->
                exclude.poetId == poemBookmark.poetId && exclude.poemId == poemBookmark.poemId
            }
        }
    }

fun Sequence.contains(value: PoemBookmark): Boolean =
    this.value.mapKeys { it.key.id }[value.poetId]?.map { it.poemId }?.contains(value.poemId) ?: false