package com.cvetyshayasiren.poetrybook.data.repository

import com.cvetyshayasiren.poetrybook.data.models.DataPoemsSequence
import com.cvetyshayasiren.poetrybook.data.models.DataPoets
import com.cvetyshayasiren.poetrybook.data.models.DataRandomState
import com.cvetyshayasiren.poetrybook.data.models.DataStyleState
import com.cvetyshayasiren.poetrybook.data.models.toPoemSequence
import com.cvetyshayasiren.poetrybook.data.models.toPoets
import com.cvetyshayasiren.poetrybook.data.models.toRandomState
import com.cvetyshayasiren.poetrybook.data.models.toStyleState
import com.cvetyshayasiren.poetrybook.domain.PoemsSequence
import com.cvetyshayasiren.poetrybook.domain.Poets
import com.cvetyshayasiren.poetrybook.domain.RandomState
import com.cvetyshayasiren.poetrybook.domain.StyleState
import com.cvetyshayasiren.poetrybook.domain.repository.FavoritesRepository
import com.cvetyshayasiren.poetrybook.domain.repository.HistoryRepository
import com.cvetyshayasiren.poetrybook.domain.repository.PoetryBookRepository
import com.cvetyshayasiren.poetrybook.domain.repository.RandomStateRepository
import com.cvetyshayasiren.poetrybook.domain.repository.StyleStateRepository
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
        Json.encodeToString<DataPoemsSequence>(DataPoemsSequence.fromPoemSequence(PoemsSequence()))

    override fun getFavorites(): PoemsSequence = Json.decodeFromString<DataPoemsSequence>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toPoemSequence()

    override fun saveFavorites(favorites: PoemsSequence) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataPoemsSequence>(DataPoemsSequence.fromPoemSequence(favorites))
        )
    }
}

class HistoryRepositoryImplementation(): HistoryRepository {
    private val settings = Settings()
    private val key = "history"
    private val default =
        Json.encodeToString<DataPoemsSequence>(DataPoemsSequence.fromPoemSequence(PoemsSequence()))

    override fun getHistory(): PoemsSequence = Json.decodeFromString<DataPoemsSequence>(
        string = settings.getString(
            key = key,
            defaultValue = default
        )
    ).toPoemSequence()

    override fun saveHistory(history: PoemsSequence) {
        settings.putString(
            key = key,
            value = Json.encodeToString<DataPoemsSequence>(DataPoemsSequence.fromPoemSequence(history))
        )
    }
}