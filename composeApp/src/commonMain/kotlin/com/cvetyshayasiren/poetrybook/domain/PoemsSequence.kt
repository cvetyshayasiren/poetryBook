package com.cvetyshayasiren.poetrybook.domain

import kotlin.jvm.JvmInline

@JvmInline
value class PoemsSequence(val value: Map<Int, List<Int>>)