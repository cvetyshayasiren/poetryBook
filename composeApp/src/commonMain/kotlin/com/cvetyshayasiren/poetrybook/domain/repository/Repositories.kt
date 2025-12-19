package com.cvetyshayasiren.poetrybook.domain.repository

import com.cvetyshayasiren.poetrybook.domain.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.Poets
import com.cvetyshayasiren.poetrybook.domain.RandomState
import com.cvetyshayasiren.poetrybook.domain.StyleState

interface PoetryBookRepository {
    suspend fun getBook(): Poets
}

interface StyleStateRepository {
    fun getStyleState(): StyleState
    fun saveStyleState(styleState: StyleState)
}

interface RandomStateRepository {
    fun getRandomState(): RandomState
    fun saveRandomState(randomState: RandomState)
}

interface FavoritesRepository {
    fun getFavorites(): PoemsSequence
    fun saveFavorites(favorites: PoemsSequence)
}

interface HistoryRepository {
    fun getHistory(): PoemsSequence
    fun saveHistory(history: PoemsSequence)
}

