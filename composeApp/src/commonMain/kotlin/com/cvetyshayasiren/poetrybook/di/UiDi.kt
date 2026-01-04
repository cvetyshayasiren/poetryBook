package com.cvetyshayasiren.poetrybook.di

import com.cvetyshayasiren.poetrybook.ui.store.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val uiDiModule = DI.Module(name = "uiDi") {
    bindSingleton { StyleStore(repository = instance()) }
    bindSingleton { RandomStore(repository = instance()) }
    bindSingleton { NavigationStore() }
    bindSingleton { PoetryBookStore(repository = instance()) }
    bindSingleton { FavoritesStore(repository = instance()) }
}