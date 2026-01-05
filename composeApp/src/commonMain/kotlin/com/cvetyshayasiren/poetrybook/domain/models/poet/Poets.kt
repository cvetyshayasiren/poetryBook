package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.utils.randomExclude

typealias Poets = List<Poet>

fun Poets.randomPoem(poetId: Int? = null, excludePoem: Poem?): Poem =
    this[poetId ?: (0..< size).random()].poems.randomExclude(exclude = excludePoem)

fun Poets.nextPoem(bookmark: PoemBookmark): Poem = getPoem(
    poetId = bookmark.poetId,
    poemId = (bookmark.poemId + 1) % getLastPoemId(bookmark.poetId)
)

fun Poets.previousPoem(bookmark: PoemBookmark): Poem = getPoem(
    poetId = bookmark.poetId,
    poemId = (getLastPoemId(bookmark.poetId) + bookmark.poemId - 1)
            % getLastPoemId(bookmark.poetId)
)

fun Poets.getPoem(bookmark: PoemBookmark): Poem = this[bookmark.poetId].poems[bookmark.poemId]

fun Poets.getPoem(poetId: Int, poemId: Int) = this[poetId].poems[poemId]

fun Poets.getPoemsCount(poetId: Int): Int = this[poetId].poems.size

fun Poets.getLastPoemId(poetId: Int): Int = getPoemsCount(poetId) - 1