package com.cvetyshayasiren.poetrybook.domain.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun currentTime(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalDateTime.prettyTimeString(): String {
    val format = LocalDateTime.Format { byUnicodePattern("HH:mm:ss:SSSS") }
    return format(format)
}
