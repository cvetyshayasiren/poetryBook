package com.cvetyshayasiren.poetrybook.data

import com.cvetyshayasiren.poetrybook.data.models.DataPoets
import com.cvetyshayasiren.poetrybook.domain.CommonRepository
import com.cvetyshayasiren.poetrybook.domain.Poems
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import poetrybook.composeapp.generated.resources.Res

class CommonRepositoryImpl: CommonRepository {
    val settings = Settings()

    fun getDefault(): DataPoets = listOf()
    suspend fun getData(): DataPoets {
        val data = Json.decodeFromString<DataPoets>(
            string = Res.readBytes(
                path = "files/poems.json"
            ).decodeToString()
        )
        return data
    }

    fun saveTest(string: String) {
        settings.putString(key = "testSave", value = string)
    }

    fun loadTest(): String = settings.getString(key = "testSave", defaultValue = "default")
}