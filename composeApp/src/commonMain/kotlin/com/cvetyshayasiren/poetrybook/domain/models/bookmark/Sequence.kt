package com.cvetyshayasiren.poetrybook.domain.models.bookmark

import com.cvetyshayasiren.poetrybook.domain.utils.randomExcludeBy
import com.cvetyshayasiren.poetrybook.domain.utils.toLinkedHashSet
import kotlin.collections.get
import kotlin.jvm.JvmInline

@JvmInline
value class Sequence<K: PoetBookmark, E: Bookmark>(val value: Map<out K, LinkedHashSet<E>>) {

    fun check() {
        value.forEach { (k, v) ->
            v.forEach { b ->
                require(b.poetId == k.id) { "Poets' IDs don't match at ${k.id} and ${b.poetId}" }
            }
        }
    }

    fun toBasicSequence(): BasicSequence =
        BasicSequence(
            value = buildMap {
                value.forEach { (poetBookmark, bookmarks) ->
                    set(
                        key = poetBookmark.toBasicPoetBookmark(),
                        value = bookmarks.map { it.toBasicPoemBookmark() }.toLinkedHashSet()
                    )
                }
            }
        )
}

typealias BasicSequence = Sequence<PoetBookmark.Basic, Bookmark.Basic>

fun Sequence<PoetBookmark, Bookmark>.random(exclude: Bookmark? = null): Bookmark =
    value.values.map { it.toList() }.reduce { a, b -> a + b }.run {
        when(exclude == null) {
            true -> random()
            false -> randomExcludeBy { poemBookmark ->
                exclude.poetId == poemBookmark.poetId && exclude.poemId == poemBookmark.poemId
            }
        }
    }

fun Sequence<PoetBookmark, Bookmark>.contains(value: Bookmark): Boolean =
    this.value.mapKeys { it.key.id }[value.poetId]?.map { it.poemId }?.contains(value.poemId) ?: false

fun BasicSequence.add(bookmark: Bookmark): BasicSequence {
    val poetBookmark = bookmark.toBasicPoetBookmark()
    val bookmarks = value[poetBookmark] ?: linkedSetOf()
    bookmarks.add(bookmark.toBasicPoemBookmark())
    val newMap = this.value.toMutableMap()
    newMap[poetBookmark] = bookmarks
    return BasicSequence(value = newMap)
}

fun BasicSequence.delete(bookmark: Bookmark): BasicSequence {
    val poetBookmark = bookmark.toBasicPoetBookmark()
    val bookmarks = value[poetBookmark] ?: linkedSetOf()
    bookmarks.remove(bookmark.toBasicPoemBookmark())
    val newMap = this.value.toMutableMap()
    when(bookmarks.isEmpty()) {
        true -> newMap.remove(poetBookmark)
        false -> newMap[poetBookmark] = bookmarks
    }
    return BasicSequence(value = newMap)
}