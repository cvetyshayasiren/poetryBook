package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.poet.Poet
import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import kotlinx.serialization.Serializable

typealias DataPoets = List<DataPoet>

@Serializable
data class DataPoet(
    val id: Int,
    val name: String,
    val poems: DataPoems
)

fun DataPoet.toPoet(): Poet = Poet(
    id = this.id,
    name = this.name,
    poems = this.poems.toPoems()
)

fun DataPoets.toPoets(): Poets = this.map { it.toPoet() }
