package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.utils.randomExcludeBy

typealias Poets = List<Poet>

fun Poets.getPoet(bookmark: PoetBookmark): Poet = this[bookmark.id]

fun Poets.getPoetName(bookmark: PoetBookmark): String = this[bookmark.id].name

fun Poets.getPoemsSize(bookmark: PoetBookmark): Int = this[bookmark.id].poems.size

fun Poets.getPoem(bookmark: Bookmark): Poem = this[bookmark.poetId].poems[bookmark.poemId]

fun Poets.nextPoem(bookmark: Bookmark): Poem =
    getPoem(poetId = bookmark.poetId, poemId = (bookmark.poemId + 1) % lastPoemIndex(bookmark.poetId))

fun Poets.previousPoem(bookmark: Bookmark): Poem {
    val lastPoemIndex = lastPoemIndex(bookmark.poetId)
    return getPoem(poetId = bookmark.poetId, poemId = (lastPoemIndex + bookmark.poemId - 1) % lastPoemIndex)
}

fun Poets.randomPoem(exclude: Bookmark? = null, poetBookmark: PoetBookmark? = null): Poem =
    this[poetBookmark?.id ?: (0..lastIndex).random()].poems.run {
        when(exclude == null) {
            true -> random()
            false -> randomExcludeBy { it.poetId == exclude.poetId && it.id == exclude.poemId }
        }
    }

private fun Poets.getPoem(poetId: Int, poemId: Int): Poem = this[poetId].poems[poemId]

private fun Poets.lastPoemIndex(poetId: Int): Int = this[poetId].poems.lastIndex

fun Poets.check() {
    forEachIndexed { index, poet ->
        require(index == poet.id) { "The poet [${poet.id}|${poet.name}] failed verification" }

        poet.poems.forEachIndexed { index, poem ->
            require(poet.id == poem.poetId)
            { "The poem [${poem.id}|${poem.title}] failed \"poet.id\" verification" }
            require(index == poem.id) { "The poem [${poem.id}|${poem.title}] failed verification" }
        }
    }
}