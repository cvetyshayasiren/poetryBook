package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.BasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poem.TitledPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.TitledPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.utils.randomExclude
import kotlin.jvm.JvmInline

@JvmInline
value class PresentationPoets(val value: Map<PoetBookmark, TitledPoemBookmarks> = mapOf())

fun PresentationPoets.toPoetsSequence(): PoetsSequence = PoetsSequence(
    value = value
        .mapKeys { it.key.id }
        .mapValues { bookmarks -> PoemsSequence(value = bookmarks.value.map { it.poetId }) }
)

fun PresentationPoets.randomPoemBookmark(excludeBookmark: PoemBookmark? = null): PoemBookmark {
    val randomPoetBookmark = value.keys.random()
    val poemIds = value[randomPoetBookmark]?.map { it.poemId } ?: listOf(0)
    val randomPoemId = when(randomPoetBookmark.id == excludeBookmark?.poetId) {
        true -> poemIds.randomExclude(exclude = excludeBookmark.poemId)
        false -> poemIds.random()
    }
    return BasicPoemBookmark(randomPoetBookmark.id, randomPoemId)
}

fun PresentationPoets.addPoem(bookmark: TitledPoemBookmark): PresentationPoets {
    val mutableMap = value.toMutableMap()
    val poetBookmark = bookmark.toPoetBookmark()
    val poems = getBookmarks(poetBookmark)
    mutableMap[poetBookmark] = poems
    return PresentationPoets(value = mutableMap)
}

fun PresentationPoets.deletePoem(bookmark: TitledPoemBookmark): PresentationPoets {
    val mutableMap = value.toMutableMap()
    val poetBookmark = bookmark.toPoetBookmark()
    val poemsList = getBookmarks(poetBookmark).toMutableList()
    poemsList.remove(element = bookmark)
    when(poemsList.isEmpty()) {
        true -> mutableMap.remove(poetBookmark)
        false -> mutableMap[poetBookmark] = poemsList
    }
    return PresentationPoets(value = mutableMap)
}

fun PresentationPoets.addPoet(poets: PresentationPoets): PresentationPoets =
    PresentationPoets(value = this.value + poets.value)

fun PresentationPoets.deletePoet(poetId: Int): PresentationPoets {
    val mutableMap = value.toMutableMap().filter { it.key.id != poetId }
    return PresentationPoets(value = mutableMap)
}

fun PresentationPoets.getBookmarks(bookmark: PoetBookmark): TitledPoemBookmarks =
    this.value.getOrElse(key = bookmark, defaultValue = { listOf() })