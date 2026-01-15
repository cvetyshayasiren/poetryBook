package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poems

data class Poet(
    val id: Int,
    val name: String,
    val poems: Poems
)


