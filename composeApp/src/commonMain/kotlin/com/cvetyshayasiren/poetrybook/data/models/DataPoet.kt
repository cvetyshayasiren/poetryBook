package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.Poems
import kotlinx.serialization.Serializable

typealias DataPoets = List<DataPoet>

@Serializable
data class DataPoet(
    val id: Int,
    val lastPoemId: Int,
    val name: String,
    val poems: DataPoems
)
