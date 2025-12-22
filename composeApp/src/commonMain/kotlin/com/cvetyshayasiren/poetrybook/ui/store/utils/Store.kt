package com.cvetyshayasiren.poetrybook.ui.store.utils

interface Store<I> {
    fun sendIntent(intent: I)
}