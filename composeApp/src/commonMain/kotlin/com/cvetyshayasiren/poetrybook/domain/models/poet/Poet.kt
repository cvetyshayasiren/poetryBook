package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poems
import com.cvetyshayasiren.poetrybook.domain.utils.randomExclude

data class Poet(
    val id: Int,
    val name: String,
    val poems: Poems
)

fun Poet.randomPoem(exclude: Poem? = null): Poem = poems.randomExclude(exclude = exclude)


