package com.cvetyshayasiren.poetrybook.di

import com.cvetyshayasiren.poetrybook.data.repository.*
import com.cvetyshayasiren.poetrybook.domain.repository.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val dataDiModule = DI.Module(name = "dataDi") {
    bindSingleton<PoetryBookRepository> { PoetryBookRepositoryImplementation() }
    bindSingleton<StyleStateRepository> { StyleStateRepositoryImplementation() }
    bindSingleton<RandomStateRepository> { RandomStateRepositoryImplementation() }
    bindSingleton<FavoritesRepository> { FavoritesRepositoryImplementation() }
    bindSingleton<HistoryRepository> { HistoryRepositoryImplementation() }
}