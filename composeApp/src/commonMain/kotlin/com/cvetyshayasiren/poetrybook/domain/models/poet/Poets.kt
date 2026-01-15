package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.utils.randomExcludeBy

typealias Poets = List<Poet>

fun Poets.getPoem(bookmark: PoemBookmark): Poem = this[bookmark.poetId].poems[bookmark.poemId]

fun Poets.nextPoem(bookmark: PoemBookmark): Poem =
    getPoem(poetId = bookmark.poetId, poemId = (bookmark.poemId + 1) % lastPoemIndex(bookmark.poetId))

fun Poets.previousPoem(bookmark: PoemBookmark): Poem {
    val lastPoemIndex = lastPoemIndex(bookmark.poetId)
    return getPoem(poetId = bookmark.poetId, poemId = (lastPoemIndex + bookmark.poemId - 1) % lastPoemIndex)
}

fun Poets.randomPoem(exclude: PoemBookmark? = null, poetBookmark: PoetBookmark? = null): Poem =
    this[poetBookmark?.id ?: (0..lastIndex).random()].poems.run {
        when(exclude == null) {
            true -> random()
            false -> randomExcludeBy { it.poetId == exclude.poetId && it.id == exclude.poemId }
        }
    }

private fun Poets.getPoem(poetId: Int, poemId: Int): Poem = this[poetId].poems[poemId]

private fun Poets.lastPoemIndex(poetId: Int): Int = this[poetId].poems.lastIndex