package com.cvetyshayasiren.poetrybook.data.repository

import com.cvetyshayasiren.poetrybook.data.models.*
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Sequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import com.cvetyshayasiren.poetrybook.domain.models.style.StyleState
import com.cvetyshayasiren.poetrybook.domain.repository.*
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import poetrybook.composeapp.generated.resources.Res

class PoetryBookRepositoryImplementation(): PoetryBookRepository {
    override suspend fun getBook(): Poets = Json.decodeFromString<DataPoets>(
        string = Res.readBytes(
            path = "files/poems.json"
        ).decodeToString()
    ).toPoets()
}

class StyleStateRepositoryImplementation(): StyleStateRepository {
    private val settings = Settings()
    private val key = "styleState"
    private val default = Json.encodeToString(DataStyleState.fromStyleState(StyleState()))

    override fun getStyleState(): StyleState = Json.decodeFromString<DataStyleState>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toStyleState()

    override fun saveStyleState(styleState: StyleState) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataStyleState>(DataStyleState.fromStyleState(styleState))
        )
    }
}

class RandomStateRepositoryImplementation(): RandomStateRepository {
    private val settings = Settings()
    private val key = "randomState"
    private val default =
        Json.encodeToString<DataRandomState>(DataRandomState.fromRandomState(RandomState()))

    override fun getRandomState(): RandomState = Json.decodeFromString<DataRandomState>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toRandomState()

    override fun saveRandomState(randomState: RandomState) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataRandomState>(DataRandomState.fromRandomState(randomState))
        )
    }
}

class FavoritesRepositoryImplementation(): FavoritesRepository {
    private val settings = Settings()
    private val key = "favorites"
    private val default =
        Json.encodeToString<DataSequence>(DataSequence.fromSequence(Sequence(value = mapOf())))

    override fun getFavorites(): Sequence = Json.decodeFromString<DataSequence>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toSequence()

    override fun saveFavorites(favorites: Sequence) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataSequence>(DataSequence.fromSequence(favorites))
        )
    }
}

class HistoryRepositoryImplementation(): HistoryRepository {
    private val settings = Settings()
    private val key = "history"
    private val default =
        Json.encodeToString<DataDatedPoemBookmarks>(listOf())

    override fun getHistory(): DatedPoemBookmarks = Json.decodeFromString<DataDatedPoemBookmarks>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toDatedPoemBookmarks()

    override fun saveHistory(history: DatedPoemBookmarks) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataDatedPoemBookmarks>(fromDatedPoetBookmarks(history))
        )
    }
}