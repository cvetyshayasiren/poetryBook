package com.cvetyshayasiren.poetrybook.domain.models.random

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark

sealed interface RandomPoemBehaviour {
    data object SamePoet: RandomPoemBehaviour
    data object RandomPoet: RandomPoemBehaviour

    data class CertainPoet(val poetBookmark: PoetBookmark): RandomPoemBehaviour

    data object FromFavorites: RandomPoemBehaviour

    fun prettyName(): String = when(this) {
        is CertainPoet -> "выбрать"
        FromFavorites -> "избранное"
        RandomPoet -> "случайно"
        SamePoet -> "повторять"
    }
}