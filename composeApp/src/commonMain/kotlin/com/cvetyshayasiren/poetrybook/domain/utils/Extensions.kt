package com.cvetyshayasiren.poetrybook.domain.utils

fun<T> Collection<T>.random(exclude: T): T {
    if(this.size == 1) { return this.first() }
    val candidate = this.random()
    if(candidate == exclude) { return random(exclude) }
    return candidate
}