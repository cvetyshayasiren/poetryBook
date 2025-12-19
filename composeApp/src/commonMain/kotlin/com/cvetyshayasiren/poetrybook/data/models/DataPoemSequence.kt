package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.PoemsSequence
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class DataPoemsSequence(val value: Map<Int, List<Int>>) {
    companion object {
        fun fromPoemSequence(value: PoemsSequence): DataPoemsSequence = DataPoemsSequence(value = value.value)
    }
}

fun DataPoemsSequence.toPoemSequence(): PoemsSequence = PoemsSequence(value = this.value)