package com.cvetyshayasiren.poetrybook.domain.utils

import com.cvetyshayasiren.poetrybook.domain.models.poet.Poets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun Poets.simpleSearch(text: String): SearchResultPoemBookmarks = buildList {
    val pattern = Regex(pattern = text, option = RegexOption.IGNORE_CASE)
    this@simpleSearch.forEach { poet ->
        poet.poems.forEach { poem ->
            val poetNameRanges = pattern.findAll(poem.poetName).map { it.range }.toList()
            val titleRanges = pattern.findAll(poem.title).map { it.range }.toList()
            if(poetNameRanges.isNotEmpty() || titleRanges.isNotEmpty()) {
                add(
                    SearchResultPoemBookmark(
                        poetId = poet.id,
                        poemId = poem.id,
                        poetName = SearchResultPoemBookmark.SearchResult(
                            text = poem.poetName,
                            ranges = poetNameRanges
                        ),
                        title = SearchResultPoemBookmark.SearchResult(
                            text = poem.title,
                            ranges = titleRanges
                        )
                    )
                )
            }
        }
    }
}

fun Poets.hardSearch(text: String, onFinish: (() -> Unit)? = null): Flow<SearchResultPoemBookmark> = flow {
    val pattern = Regex(pattern = text, option = RegexOption.IGNORE_CASE)
    this@hardSearch.forEach { poet ->
        poet.poems.forEach { poem ->
            val poetNameRanges = pattern.findAll(poem.poetName).map { it.range }.toList()
            val titleRanges = pattern.findAll(poem.title).map { it.range }.toList()
            val textRanges = pattern.findAll(poem.text).map { it.range }.toList()
            if(poetNameRanges.isNotEmpty() || titleRanges.isNotEmpty() || textRanges.isNotEmpty()) {
                emit(
                    value = SearchResultPoemBookmark(
                        poetId = poet.id,
                        poemId = poem.id,
                        poetName = SearchResultPoemBookmark.SearchResult(
                            text = poem.poetName,
                            ranges = poetNameRanges
                        ),
                        title = SearchResultPoemBookmark.SearchResult(
                            text = poem.title,
                            ranges = titleRanges
                        ),
                        text = SearchResultPoemBookmark.SearchResult(
                            text = poem.text,
                            ranges = textRanges
                        )
                    )
                )
            }
        }
    }
    onFinish?.let { it() }
}