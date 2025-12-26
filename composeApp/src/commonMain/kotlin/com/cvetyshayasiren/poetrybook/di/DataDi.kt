package com.cvetyshayasiren.poetrybook.di

import com.cvetyshayasiren.poetrybook.data.repository.FavoritesRepositoryImplementation
import com.cvetyshayasiren.poetrybook.data.repository.HistoryRepositoryImplementation
import com.cvetyshayasiren.poetrybook.data.repository.PoetryBookRepositoryImplementation
import com.cvetyshayasiren.poetrybook.data.repository.RandomStateRepositoryImplementation
import com.cvetyshayasiren.poetrybook.data.repository.StyleStateRepositoryImplementation
import com.cvetyshayasiren.poetrybook.domain.repository.FavoritesRepository
import com.cvetyshayasiren.poetrybook.domain.repository.HistoryRepository
import com.cvetyshayasiren.poetrybook.domain.repository.PoetryBookRepository
import com.cvetyshayasiren.poetrybook.domain.repository.RandomStateRepository
import com.cvetyshayasiren.poetrybook.domain.repository.StyleStateRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val dataDiModule = DI.Module(name = "dataDi") {
    bindSingleton<PoetryBookRepository> { PoetryBookRepositoryImplementation() }
    bindSingleton<StyleStateRepository> { StyleStateRepositoryImplementation() }
    bindSingleton<RandomStateRepository> { RandomStateRepositoryImplementation() }
    bindSingleton<FavoritesRepository> { FavoritesRepositoryImplementation() }
    bindSingleton<HistoryRepository> { HistoryRepositoryImplementation() }
}