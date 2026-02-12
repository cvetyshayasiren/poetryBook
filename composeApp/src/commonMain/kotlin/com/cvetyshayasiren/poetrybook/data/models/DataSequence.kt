package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.*
import com.cvetyshayasiren.poetrybook.domain.utils.toLinkedHashSet
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.math.abs

@Serializable
@JvmInline
value class DataSequence(val value: Map<Int, DataPoemBookmarks>) {
    fun toSequence(): BasicSequence = Sequence(
        value = buildMap {
            value.forEach { (poetId, bookmarks) ->
                set(
                    key = PoetBookmark.Basic(id = poetId),
                    value = bookmarks.toPoemBookmarks(poetId = poetId).toLinkedHashSet()
                )
            }
        }
    )

    companion object {
        fun fromSequence(sequence: Sequence<out PoetBookmark, out Bookmark>): DataSequence = DataSequence(
            value = buildMap {
                sequence.value.forEach { (poetBookmark, bookmarks) ->
                    set(
                        key = poetBookmark.id,
                        value = DataPoemBookmarks.fromPoemBookmarks(bookmarks.toList())
                    )
                }
            }
        )
    }
}

@Serializable
@JvmInline
value class DataPoemBookmarks(val value: List<Int>) {
    fun toPoemBookmarks(poetId: Int): BasicBookmarks = buildList {
        require(value.isNotEmpty()) { "The poems sequence cannot be empty" }
        var previousPoemId: Int = value.first()

        value.forEach { poemId ->
            when(poemId < 0) {
                true -> {
                    addAll(
                        elements = (previousPoemId + 1..abs(poemId)).map {
                            Bookmark.Basic(poetId = poetId, poemId = it)
                        }
                    )
                    previousPoemId = abs(poemId)
                }
                false -> {
                    add(Bookmark.Basic(poetId = poetId, poemId = poemId))
                    previousPoemId = poemId
                }
            }
        }
    }

    companion object {
        fun fromPoemBookmarks(bookmarks: Bookmarks): DataPoemBookmarks = DataPoemBookmarks(
            value = buildList {
                require(bookmarks.isNotEmpty()) { "The poems sequence cannot be empty" }
                var previousPoemId: Int = bookmarks.first().poemId
                    .also { if(bookmarks.size == 1) add(it) }
                val sequenceLastIndex = bookmarks.size - 2
                bookmarks.drop(1).forEachIndexed { index, bookmark ->
                    val poemId = bookmark.poemId
                    val isInRange = (poemId - abs(previousPoemId)) == 1
                    when(isInRange) {
                        true -> {
                            val isPreviousNotInRange = previousPoemId >= 0
                            if(isPreviousNotInRange) { add(previousPoemId) }
                            previousPoemId = -poemId
                        }
                        false -> {
                            add(previousPoemId)
                            previousPoemId = poemId
                        }
                    }
                    if(index == sequenceLastIndex) { add(previousPoemId) }
                }
            }
        )
    }
}