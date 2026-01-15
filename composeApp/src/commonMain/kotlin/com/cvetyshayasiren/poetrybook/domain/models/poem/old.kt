package com.cvetyshayasiren.poetrybook.domain.models.poem

//data class TitledPoemBookmark(
//    override val poetId: Int,
//    override val poemId: Int,
//    val poetName: String,
//    val title: String
//): PoemBookmark {
//    fun toPoetBookmark(): PoetBookmark = PoetBookmark(id = poetId, name = poetName)
//}
//
//data class SearchResultPoemBookmark(
//    override val poetId: Int,
//    override val poemId: Int,
//    val poetName: SearchResult,
//    val title: SearchResult,
//    val text: SearchResult? = null
//): PoemBookmark {
//    data class SearchResult(
//        val text: String,
//        val ranges: List<IntRange>
//    )
//}
//
//typealias TitledPoemBookmarks = List<TitledPoemBookmark>
//typealias SearchResultPoemBookmarks = List<SearchResultPoemBookmark>