package com.cvetyshayasiren.poetrybook.domain.utils

data class PrettyLabel(
    val labelEn: String,
    val labelRu: String
) {
    fun lowerEn(): String = labelEn.lowercase()
    fun upperEn(): String = labelEn.uppercase()
    fun capitaliseEn(): String = labelEn.capitalise()

    fun lowerRu(): String = labelRu.lowercase()
    fun upperRu(): String = labelRu.uppercase()
    fun capitaliseRu(): String = labelRu.capitalise()

    private fun String.capitalise(): String =
        this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}