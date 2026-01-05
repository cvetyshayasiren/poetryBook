package com.cvetyshayasiren.poetrybook.domain.models.random

import kotlin.enums.EnumEntries

sealed interface RandomPoemBehaviour {
    data object SamePoet: RandomPoemBehaviour
    data object RandomPoet: RandomPoemBehaviour

    data class CertainPoet(val poetId: Int): RandomPoemBehaviour

    data object FromFavorites: RandomPoemBehaviour
}