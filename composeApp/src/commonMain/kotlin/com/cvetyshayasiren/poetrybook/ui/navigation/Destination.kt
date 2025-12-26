package com.cvetyshayasiren.poetrybook.ui.navigation

typealias Destinations = List<Destination>

sealed interface Destination {
    data object Book: Destination
    data object Settings: Destination
    data object Favorites: Destination
    data object History: Destination
    data object Search: Destination

    companion object {
        val list = listOf<Destination>(Book, Settings, Favorites, History, Search)
        private val expandedNavList = listOf(History, Favorites)
        private val notExpandedNavList = listOf(History, Favorites, Settings)

        fun navList(isExpanded: Boolean) = if(isExpanded) expandedNavList else notExpandedNavList
    }
}