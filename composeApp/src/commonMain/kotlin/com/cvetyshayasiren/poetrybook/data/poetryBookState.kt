package com.cvetyshayasiren.poetrybook.data

import com.cvetyshayasiren.poetrybook.domain.Poets

sealed interface PoetryBookState {
    data object Loading: PoetryBookState
    data class Prepared(val book: Poets): PoetryBookState
}