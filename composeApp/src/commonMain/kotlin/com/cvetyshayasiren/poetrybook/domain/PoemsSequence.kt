package com.cvetyshayasiren.poetrybook.domain

import kotlin.jvm.JvmInline

@JvmInline
value class PoemsSequence(val value: Map<Int, List<Int>> = mapOf()) {
    companion object {
        val DEFAULT: PoemsSequence = PoemsSequence(value = mapOf<Int, List<Int>>())
    }
}