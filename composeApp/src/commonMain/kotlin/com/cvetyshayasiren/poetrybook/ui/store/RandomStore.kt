package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import com.cvetyshayasiren.poetrybook.domain.repository.RandomStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import org.kodein.di.instance
import kotlin.getValue

data class RandomStoreState(
    val randomState: RandomState,
    val poets: NamedPoetBookmarks
)

sealed interface RandomStoreIntent {
    class SetNextPoemBehaviour(val nextPoemBehaviour: RandomPoemBehaviour): RandomStoreIntent
    class SetIsRandomiseSeed(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseIsm(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseThemeMod(val value: Boolean): RandomStoreIntent
}

sealed interface RandomStoreEffect

class RandomStoreReducer(
    val repository: RandomStateRepository
): Reducer<RandomStoreState, RandomStoreIntent, RandomStoreEffect> {
    override suspend fun reduce(
        state: RandomStoreState,
        intent: RandomStoreIntent
    ): ReducerResult<RandomStoreState, out RandomStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is RandomStoreIntent.SetIsRandomiseIsm ->
                state.copy(randomState = state.randomState.copy(isRandomiseIsm = intent.value))
            is RandomStoreIntent.SetIsRandomiseSeed ->
                state.copy(randomState = state.randomState.copy(isRandomiseSeed = intent.value))
            is RandomStoreIntent.SetIsRandomiseThemeMod ->
                state.copy(randomState = state.randomState.copy(isRandomiseThemeMode = intent.value))
            is RandomStoreIntent.SetNextPoemBehaviour ->
                state.copy(randomState = state.randomState.copy(randomPoemBehaviour = intent.nextPoemBehaviour))
        }.also { repository.saveRandomState(it.randomState) }
    )
}


class RandomStore(
    repository: RandomStateRepository
): Store<RandomStoreState, RandomStoreIntent, RandomStoreEffect>(
    defaultState = RandomStoreState(
        randomState = RandomState(),
        poets = listOf()
    ),
    initialiseState = {
        RandomStoreState(
            randomState = repository.getRandomState(),
            poets = NamedPoetBookmark.getList()
        )
    },
    reducer = RandomStoreReducer(repository = repository)
)

//models

data class NamedPoetBookmark(
    override val id: Int,
    val name: String
): PoetBookmark {
    companion object {
        suspend fun getList(): NamedPoetBookmarks {
            val poetryBookStore: PoetryBookStore by di.instance()
            val book = poetryBookStore.getBook()
            return book.map { poet -> NamedPoetBookmark(id = poet.id, name = poet.name) }
        }
    }
}

typealias NamedPoetBookmarks = List<NamedPoetBookmark>