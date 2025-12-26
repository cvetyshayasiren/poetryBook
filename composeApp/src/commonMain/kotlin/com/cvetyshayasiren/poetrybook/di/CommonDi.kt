package com.cvetyshayasiren.poetrybook.di

import org.kodein.di.DI

val di = DI {
    importAll(dataDiModule, domainDiModule, uiDiModule)
}