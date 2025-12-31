package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poems

data class Poet(
    val id: Int,
    val name: String,
    val poems: Poems
)

fun Poet.randomPoem(exclude: Poem): Poem {
    if(poems.size == 1) { return exclude }
    val candidate = poems.random()
    if (candidate == exclude) { randomPoem(exclude) }
    return candidate
}


