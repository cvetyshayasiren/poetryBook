package com.cvetyshayasiren.poetrybook.data.models

import kotlinx.serialization.Serializable

typealias DataPoems = List<DataPoem>

@Serializable
data class DataPoem(
    val id: Int,
    val poetId: Int,
    val lastPoemId: Int,
    val poetName: String,
    val title: String,
    val text: String
)
