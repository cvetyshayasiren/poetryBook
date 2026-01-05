package com.cvetyshayasiren.poetrybook.domain.models.poem

import com.cvetyshayasiren.poetrybook.domain.utils.randomExclude
import kotlin.jvm.JvmInline

@JvmInline
value class PoemsSequence(val value: List<Int> = listOf()) {

    fun random(exclude: Int? = null): Int = value.randomExclude(exclude = exclude)
}

