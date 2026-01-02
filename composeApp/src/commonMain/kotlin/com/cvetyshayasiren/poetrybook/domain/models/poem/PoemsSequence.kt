package com.cvetyshayasiren.poetrybook.domain.models.poem

import com.cvetyshayasiren.poetrybook.domain.utils.random
import kotlin.jvm.JvmInline

@JvmInline
value class PoemsSequence(val value: List<Int> = listOf()) {

    fun random(exclude: Int): Int = value.random(exclude = exclude)
}

