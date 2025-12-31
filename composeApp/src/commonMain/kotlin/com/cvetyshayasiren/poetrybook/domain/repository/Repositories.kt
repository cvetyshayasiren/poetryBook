package com.cvetyshayasiren.poetrybook.domain.repository

import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import com.cvetyshayasiren.poetrybook.domain.models.poet.PoetsSequence
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import com.cvetyshayasiren.poetrybook.domain.models.style.StyleState

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
    fun getFavorites(): PoetsSequence
    fun saveFavorites(favorites: PoetsSequence)
}

interface HistoryRepository {
    fun getHistory(): PoetsSequence
    fun saveHistory(history: PoetsSequence)
}

