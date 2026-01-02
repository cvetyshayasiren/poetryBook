package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poems
import com.cvetyshayasiren.poetrybook.domain.utils.random

data class Poet(
    val id: Int,
    val name: String,
    val poems: Poems
)

fun Poet.randomPoem(exclude: Poem): Poem = poems.random(exclude = exclude)


