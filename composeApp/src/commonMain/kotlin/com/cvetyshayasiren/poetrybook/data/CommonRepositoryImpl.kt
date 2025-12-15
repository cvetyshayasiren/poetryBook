package com.cvetyshayasiren.poetrybook.data

import com.cvetyshayasiren.poetrybook.data.models.DataPoets
import com.cvetyshayasiren.poetrybook.domain.CommonRepository
import com.cvetyshayasiren.poetrybook.domain.Poems
import kotlinx.serialization.json.Json
import poetrybook.composeapp.generated.resources.Res

class CommonRepositoryImpl: CommonRepository {

    fun getDefault(): DataPoets = listOf()
    suspend fun getData(): DataPoets {
        val data = Json.decodeFromString<DataPoets>(
            string = Res.readBytes(
                path = "files/poems.json"
            ).decodeToString()
        )
        return data
    }
}