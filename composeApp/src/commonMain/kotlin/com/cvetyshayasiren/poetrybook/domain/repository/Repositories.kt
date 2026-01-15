package com.cvetyshayasiren.poetrybook.domain.repository

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Sequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
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
    fun getFavorites(): Sequence
    fun saveFavorites(favorites: Sequence)
}

interface HistoryRepository {
    fun getHistory(): DatedPoemBookmarks
    fun saveHistory(history: DatedPoemBookmarks)
}

