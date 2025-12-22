package com.cvetyshayasiren.poetrybook.ui.navigation

sealed interface Destinations {
    data object Book: Destinations
    data object Settings: Destinations
    data object Favorites: Destinations
    data object History: Destinations
    data object Search: Destinations

    companion object {
        val list = listOf<Destinations>(Book, Settings, Favorites, History, Search)
        private val expandedNavList = listOf(History, Favorites)
        private val notExpandedNavList = listOf(History, Favorites, Settings)

        fun navList(isExpanded: Boolean) = if(isExpanded) expandedNavList else notExpandedNavList
    }
}