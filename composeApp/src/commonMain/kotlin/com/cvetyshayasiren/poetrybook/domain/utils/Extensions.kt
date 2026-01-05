package com.cvetyshayasiren.poetrybook.domain.utils

fun<T> Collection<T>.randomExclude(exclude: T? = null): T {
    if(this.size == 1) { return this.first() }
    val candidate = this.random()
    if(candidate == exclude) { return randomExclude(exclude) }
    return candidate
}