package com.cvetyshayasiren.poetrybook.ui.store.utils

interface Reducer<S, I> {
    fun reduce(state: S, intent: I): S
}