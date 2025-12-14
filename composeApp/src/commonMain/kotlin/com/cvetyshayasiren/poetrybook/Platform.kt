package com.cvetyshayasiren.poetrybook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform