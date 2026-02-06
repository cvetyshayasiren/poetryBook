package com.cvetyshayasiren.poetrybook.ui.store

import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.DatedPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.toDatedPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.getPoem
import com.cvetyshayasiren.poetrybook.domain.repository.HistoryRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.kodein.di.instance

typealias HistoryStoreState = HistoryBookmarks

sealed interface HistoryStoreIntent {
    class AddBookmark(val bookmark: Bookmark): HistoryStoreIntent
    class Clear(val index: Int): HistoryStoreIntent
    data object ClearAll: HistoryStoreIntent
    data class NavigateAndSwitch(val bookmark: Bookmark): HistoryStoreIntent
}

sealed interface HistoryStoreEffect

class HistoryStateReducer(): Reducer<HistoryStoreState, HistoryStoreIntent, HistoryStoreEffect> {
    override suspend fun reduce(
        state: HistoryStoreState,
        intent: HistoryStoreIntent
    ): ReducerResult<HistoryStoreState, out HistoryStoreEffect?> = ReducerResult.build {
        newState = when(intent) {
            is HistoryStoreIntent.AddBookmark -> {
                val lastBookmark = state.lastOrNull()
                when(intent.bookmark.toBasicPoemBookmark() == lastBookmark?.toBasicPoemBookmark()) {
                    true -> state
                    false -> {
                        val newState = state + intent.bookmark.toDatedPoemBookmark().toHistoryBookmark()
                        when(newState.size > 5) {
                            true -> newState.drop(1)
                            false -> newState
                        }
                    }
                }
            }
            is HistoryStoreIntent.Clear -> {
                val mutableList = state.toMutableList()
                mutableList.removeAt(intent.index)
                mutableList
            }
            is HistoryStoreIntent.ClearAll -> listOf()
            is HistoryStoreIntent.NavigateAndSwitch -> state.also {
                val pageStore: PageStore by di.instance()
                pageStore.sendIntent(PageStoreIntent.NavigateAndSwitch(intent.bookmark))
            }
        }
    }
}

class HistoryStore(
    repository: HistoryRepository
): Store<HistoryStoreState, HistoryStoreIntent, HistoryStoreEffect> (
    defaultState = listOf(),
    initialiseState = { repository.getHistory().toHistoryBookmarks() },
    reducer = HistoryStateReducer()
) {
    init {
        launchAfterInit { savingDaemon(repository) }
    }

    suspend fun savingDaemon(repository: HistoryRepository) {
        state.collect { historyStoreState ->
            if(stateIsInit) {
                repository.saveHistory(historyStoreState.toDatedBookmarks())
            }
        }
    }
}

//models

data class HistoryBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val dateTime: LocalDateTime,
    val poetName: String,
    val title: String
): Bookmark

typealias HistoryBookmarks = List<HistoryBookmark>

suspend fun DatedBookmark.toHistoryBookmark(): HistoryBookmark {
    val poetryBookStore: PoetryBookStore by di.instance()
    val book = poetryBookStore.getBook()
    val poem = book.getPoem(this)
    return HistoryBookmark(
        poetId = poem.poetId,
        poemId = poem.id,
        dateTime = dateTime,
        poetName = poem.poetName,
        title = poem.title
    )
}

suspend fun DatedPoemBookmarks.toHistoryBookmarks(): HistoryBookmarks {
    val poetryBookStore: PoetryBookStore by di.instance()
    val book = poetryBookStore.getBook()
    return buildList {
        this@toHistoryBookmarks.forEach { datedPoem ->
            val poem = book.getPoem(datedPoem)
            add(
                HistoryBookmark(
                    poetId = poem.poetId,
                    poemId = poem.id,
                    dateTime = datedPoem.dateTime,
                    poetName = poem.poetName,
                    title = poem.title
                )
            )
        }
    }
}

fun HistoryBookmarks.toDatedBookmarks() =
    map { DatedBookmark(poetId = it.poetId, poemId = it.poemId, dateTime = it.dateTime) }