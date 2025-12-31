package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import kotlin.jvm.JvmInline

@JvmInline
value class PoetsSequence(val value: Map<Int, PoemsSequence> = mapOf()) {

    companion object {
        val DEFAULT: PoetsSequence = PoetsSequence(value = mapOf<Int, PoemsSequence>())
    }
}