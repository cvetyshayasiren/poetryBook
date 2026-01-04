package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.BasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import kotlin.jvm.JvmInline

@JvmInline
value class PoetsSequence(val value: Map<Int, PoemsSequence> = mapOf())

fun PoetsSequence.randomPoemBookmark(excludeBookmark: PoemBookmark? = null): PoemBookmark {
    val randomPoetId = value.keys.random()
    val randomPoemId = when(randomPoetId == excludeBookmark?.poetId) {
        true -> value[randomPoetId]?.random(exclude = excludeBookmark.poemId) ?: 0
        false -> value[randomPoetId]?.value?.random() ?: 0
    }
    return BasicPoemBookmark(randomPoetId, randomPoemId)
}

fun PoetsSequence.addPoem(bookmark: PoemBookmark): PoetsSequence {
    val mutableMap = value.toMutableMap()
    val poems = getPoemsSequence(bookmark.poetId)
    mutableMap[bookmark.poetId] = poems
    return PoetsSequence(value = mutableMap)
}

fun PoetsSequence.deletePoem(bookmark: PoemBookmark): PoetsSequence {
    val mutableMap = value.toMutableMap()
    val poemsList = getPoemsSequence(bookmark.poetId).value.toMutableList()
    poemsList.remove(bookmark.poemId)
    when(poemsList.isEmpty()) {
        true -> mutableMap.remove(bookmark.poetId)
        false -> mutableMap[bookmark.poetId] = PoemsSequence(value = poemsList)
    }
    return PoetsSequence(value = mutableMap)
}

fun PoetsSequence.addPoet(poetId: Int, poemsCount: Int): PoetsSequence {
    val mutableMap = value.toMutableMap()
    val poemsList = (0..poemsCount).toList()
    mutableMap[poetId] = PoemsSequence(value = poemsList)
    return PoetsSequence(value = mutableMap)
}

fun PoetsSequence.deletePoet(poetId: Int): PoetsSequence {
    val mutableMap = value.toMutableMap()
    mutableMap.remove(poetId)
    return PoetsSequence(value = mutableMap)
}

fun PoetsSequence.getPoemsSequence(poetId: Int): PoemsSequence =
    this.value.getOrElse(key = poetId, defaultValue = { PoemsSequence() })