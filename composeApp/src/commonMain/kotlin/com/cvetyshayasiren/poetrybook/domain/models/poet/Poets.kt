package com.cvetyshayasiren.poetrybook.domain.models.poet

import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark

typealias Poets = List<Poet>

fun Poets.getPoem(bookmark: PoemBookmark): Poem = this[bookmark.poetId()].poems[bookmark.poemId()]