package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poems
import kotlinx.serialization.Serializable

typealias DataPoems = List<DataPoem>

@Serializable
data class DataPoem(
    val id: Int,
    val poetId: Int,
    val poetName: String,
    val title: String,
    val text: String
)

fun DataPoem.toPoem(): Poem = Poem(
    id = this.id,
    poetId = this.poetId,
    poetName = this.poetName,
    title = this.title,
    text = this.text
)

fun DataPoems.toPoems(): Poems = this.map { it.toPoem() }
