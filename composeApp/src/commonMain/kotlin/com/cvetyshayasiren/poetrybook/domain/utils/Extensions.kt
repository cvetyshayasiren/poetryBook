package com.cvetyshayasiren.poetrybook.domain.utils

fun<T> Collection<T>.randomExclude(exclude: T? = null): T {
    if(this.size == 1) { return this.first() }
    val candidate = this.random()
    if(candidate == exclude) { return randomExclude(exclude) }
    return candidate
}

fun<T> Collection<T>.randomExcludeBy(excludeWhen: ((T) -> Boolean)? = null): T {
    if(this.size == 1) { return this.first() }
    val candidate = this.random()
    if(excludeWhen == null) { return candidate }
    if(excludeWhen(candidate)) {
        return this.filter { it != candidate }.randomExcludeBy(excludeWhen = excludeWhen)
    }
    return candidate
}