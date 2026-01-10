package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poem.TitledPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.TitledPoemBookmarks
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

fun Poets.getPresentationPoets(sequence: PoetsSequence): PresentationPoets = PresentationPoets(
    value = buildMap {
        sequence.value.forEach { (key: Int, value: PoemsSequence) ->
            val poetId = key
            val poetName = this@getPresentationPoets[key].name
            set(
                key = PoetBookmark(id = poetId, name = poetName),
                value = value.value.map { poemId ->
                    TitledPoemBookmark(
                        poetId = poetId,
                        poemId = poemId,
                        poetName = poetName,
                        title = this@getPresentationPoets[key].poems[poemId].title
                    )
                }
            )
        }
    }
)

fun Poets.getSeparatedPresentationPoets(poetId: Int): PresentationPoets = PresentationPoets(
    value = mapOf(
        PoetBookmark(
            id = poetId,
            name = this[poetId].name
        ) to
        this[poetId].poems.map {
            TitledPoemBookmark(
                poetId = poetId,
                poemId = it.id,
                poetName = it.poetName,
                title = it.title
            )
        }
    )
)