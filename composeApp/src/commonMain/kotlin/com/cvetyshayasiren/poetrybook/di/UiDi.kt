package com.cvetyshayasiren.poetrybook.di

import com.cvetyshayasiren.poetrybook.ui.store.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val uiDiModule = DI.Module(name = "uiDi") {
    bindSingleton { FavoritesStore(favoritesRepository = instance()) }
    bindSingleton { HistoryStore(repository = instance()) }
    bindSingleton { NavigationStore() }
    bindSingleton { PageStore(poetryBookStore = instance()) }
    bindSingleton { PoetryBookStore(repository = instance()) }
    bindSingleton { RandomStore(repository = instance()) }
    bindSingleton { SearchStore() }
    bindSingleton { StyleStore(repository = instance()) }
}