package com.cvetyshayasiren.poetrybook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

sealed interface Destination {
    data object Page: Destination
    data object Settings: Destination
    data object Favorites: Destination
    data object History: Destination
    data object Search: Destination

    companion object {
        val list = listOf<Destination>(Page, Settings, Favorites, History, Search)
    }
}

typealias Destinations = List<Destination>

fun Destinations.current() = last()
fun Destinations.nowIs(destination: Destination): Boolean = current() == destination
fun Destinations.isPage(): Boolean = nowIs(Destination.Page)

@Composable
fun Destinations.currentMenuIndex(isExpanded: Boolean = WindowSizeClass.isExpanded()): Int? =
    when(current()) {
        Destination.History -> 0
        Destination.Favorites -> 1
        Destination.Settings -> if(isExpanded) null else 2
        Destination.Page -> if(isExpanded) 2 else 3
        Destination.Search -> null
    }

