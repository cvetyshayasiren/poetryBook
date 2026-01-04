package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.poem.Poem
import com.cvetyshayasiren.poetrybook.domain.models.poem.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.SearchResultPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poem.SearchResultPoemBookmarks
import com.cvetyshayasiren.poetrybook.domain.models.poet.*
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.repository.PoetryBookRepository
import com.cvetyshayasiren.poetrybook.domain.utils.hardSearch
import com.cvetyshayasiren.poetrybook.domain.utils.simpleSearch
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.kodein.di.instance

sealed interface PoetryBookState {
    data object Loading: PoetryBookState
    data class Prepared(val book: Poets): PoetryBookState {

        fun getRandomPoem(current: Poem? = null): Poem {
            val randomStore: RandomStore by di.instance()
            val randomState = randomStore.state.value
            if(randomState.isNeedStyleChange()) {
                val styleStore: StyleStore by di.instance()
                styleStore.sendIntent(
                    intent = StyleStoreIntent.RandomiseStyle(
                        isRandomiseIsm = randomState.isRandomiseIsm,
                        isRandomiseSeed = randomState.isRandomiseSeed,
                        isRandomiseThemeMode = randomState.isRandomiseThemeMode
                    )
                )
            }

            return when(val behaviour = randomState.randomPoemBehaviour) {
                RandomPoemBehaviour.SamePoet -> book.randomPoem(poetId = current?.poetId, excludePoem = current)
                RandomPoemBehaviour.RandomPoet -> book.randomPoem(excludePoem = current)
                is RandomPoemBehaviour.CertainPoet -> book.randomPoem(poetId = behaviour.poetId, excludePoem = current)
                RandomPoemBehaviour.FromFavorites -> {
                    val favoritesStore: FavoritesStore by di.instance()
                    val state = favoritesStore.state.value
                    val randomBookmark = state.randomPoemBookmark(excludeBookmark = current?.toBasicPoemBookmark())
                    book.getPoem(randomBookmark)
                }
            }
        }
    }
}

sealed interface PoetryBookIntent

sealed interface PoetryBookEffect

class PoetryBookReducer: Reducer<PoetryBookState, PoetryBookIntent, PoetryBookEffect> {
    override suspend fun reduce(
        state: PoetryBookState,
        intent: PoetryBookIntent
    ): ReducerResult<PoetryBookState, out PoetryBookEffect?> = ReducerResult.build(state = state)
}

class PoetryBookStore(
    repository: PoetryBookRepository
): Store<PoetryBookState, PoetryBookIntent, PoetryBookEffect>(
    defaultState = PoetryBookState.Loading,
    initialiseState = { PoetryBookState.Prepared(book = repository.getBook()) },
    reducer = PoetryBookReducer(),
    tag = "PoetryBookStore"
) {
    suspend fun getRandomPoem(current: Poem? = null): Poem =
        getPrepared().getRandomPoem(current = current)

    suspend fun getNextPoem(bookmark: PoemBookmark): Poem = getPrepared().book.nextPoem(bookmark)
    suspend fun getPreviousPoem(bookmark: PoemBookmark): Poem = getPrepared().book.previousPoem(bookmark)

    suspend fun simpleSearch(userInput: String): SearchResultPoemBookmarks = getPrepared().book.simpleSearch(text = userInput)
    suspend fun hardSearch(userInput: String): Flow<SearchResultPoemBookmark> = getPrepared().book.hardSearch(text = userInput)

    private suspend fun getPrepared(): PoetryBookState.Prepared =
        state.first { poetryBookState -> poetryBookState is PoetryBookState.Prepared } as PoetryBookState.Prepared
}