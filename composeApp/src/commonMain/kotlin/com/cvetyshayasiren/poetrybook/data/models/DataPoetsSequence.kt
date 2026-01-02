package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.poem.BasicPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.PoetsSequence
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.math.abs

@Serializable
@JvmInline
value class DataPoetsSequence(val value: Map<Int, DataPoemsSequence>) {
    fun toPoetsSequence(): PoetsSequence = PoetsSequence(value = value.mapValues { it.value.toPoemsSequence() })

    companion object {
        fun fromPoetSequence(value: PoetsSequence): DataPoetsSequence = DataPoetsSequence(
            value = value.value.mapValues { DataPoemsSequence.fromPoemsSequence(it.value) }.toMutableMap()
        )
    }
}

@Serializable
@JvmInline
value class DataPoemsSequence(val value: List<Int>) {
    fun toPoemsSequence(): PoemsSequence = PoemsSequence(
        value = buildList<Int> {
            require(value.isNotEmpty()) { "2The poems sequence cannot be empty" }
            var previousPoemId: Int = value.first()

            value.forEach { poemId ->
                when(poemId < 0) {
                    true -> {
                        addAll((previousPoemId + 1..abs(poemId)).toList())
                        previousPoemId = abs(poemId)
                    }
                    false -> {
                        add(poemId)
                        previousPoemId = poemId
                    }
                }
            }
        }.sorted()
    )

    companion object {
        fun fromPoemsSequence(value: PoemsSequence): DataPoemsSequence = DataPoemsSequence(
            value = buildList<Int> {
                require(value.value.isNotEmpty()) { "The poems sequence cannot be empty" }
                var previousPoemId: Int = value.value.first()
                val sequenceLastIndex = value.value.size - 2
                value.value.drop(1).forEachIndexed { index, poemId ->
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
            }.sortedBy { abs(it) }
        )
    }
}