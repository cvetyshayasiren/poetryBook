package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.Poet
import com.cvetyshayasiren.poetrybook.domain.Poets
import kotlinx.serialization.Serializable

typealias DataPoets = List<DataPoet>

@Serializable
data class DataPoet(
    val id: Int,
    val lastPoemId: Int,
    val name: String,
    val poems: DataPoems
)

fun DataPoet.toPoet(): Poet = Poet(
    id = this.id,
    lastPoemId = this.lastPoemId,
    name = this.name,
    poems = this.poems.toPoems()
)

fun DataPoets.toPoets(): Poets = this.map { it.toPoet() }
