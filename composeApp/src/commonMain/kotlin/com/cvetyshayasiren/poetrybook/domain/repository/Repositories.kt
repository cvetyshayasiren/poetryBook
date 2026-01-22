package com.cvetyshayasiren.poetrybook.domain.repository

import com.cvetyshayasiren.poetrybook.domain.models.bookmark.BasicSequence
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
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
    fun getFavorites(): BasicSequence
    fun saveFavorites(favorites: Sequence<out PoetBookmark, out Bookmark>)
}

interface HistoryRepository {
    fun getHistory(): DatedPoemBookmarks
    fun saveHistory(history: DatedPoemBookmarks)
}

