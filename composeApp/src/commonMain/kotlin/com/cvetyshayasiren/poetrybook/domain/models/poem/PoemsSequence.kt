package com.cvetyshayasiren.poetrybook.domain.models.poem

import com.cvetyshayasiren.poetrybook.domain.utils.random
import kotlin.jvm.JvmInline
import kotlin.math.abs
import kotlin.random.Random

@JvmInline
value class PoemsSequence(val value: List<Int> = listOf()) {

    fun random(exclude: Int): Int = value.random(exclude = exclude)

    companion object {
        val DEFAULT: PoemsSequence = PoemsSequence(value = listOf())
    }
}

