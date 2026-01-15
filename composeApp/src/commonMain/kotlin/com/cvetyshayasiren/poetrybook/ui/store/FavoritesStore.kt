package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Sequence
import com.cvetyshayasiren.poetrybook.domain.models.poet.*
import com.cvetyshayasiren.poetrybook.domain.repository.FavoritesRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import org.kodein.di.instance

typealias FavoritesStoreState = Sequence

sealed interface FavoritesStoreIntent {
    class AddPoem(val bookmark: PoemBookmark): FavoritesStoreIntent
    class DeletePoem(val bookmark: PoemBookmark): FavoritesStoreIntent

    data object ApplySwitchPoet: FavoritesStoreIntent
    class SwitchPoet(val poetId: Int): FavoritesStoreIntent

    data object ClearFavorites: FavoritesStoreIntent

    fun needSave(): Boolean = this != ApplySwitchPoet
}

sealed interface FavoritesStoreEffect {
    data object ShowAddConfirmation: FavoritesStoreEffect
    data object ShowDeleteConfirmation: FavoritesStoreEffect
}

class FavoritesStoreReducer(
    val favoritesRepository: FavoritesRepository
): Reducer<FavoritesStoreState, FavoritesStoreIntent, FavoritesStoreEffect> {
    override suspend fun reduce(
        state: FavoritesStoreState,
        intent: FavoritesStoreIntent
    ): ReducerResult<FavoritesStoreState, out FavoritesStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is FavoritesStoreIntent.AddPoem -> {
                val titledPoemBookmark = when(intent.bookmark) {
                    is TitledPoemBookmark -> intent.bookmark
                    else -> {
                        val poetryBookStore: PoetryBookStore by di.instance()
                        poetryBookStore.getTitledPoemBookmark(intent.bookmark)
                    }
                }
                state.addPoem(titledPoemBookmark)
            }
            is FavoritesStoreIntent.DeletePoem ->  {
                val titledPoemBookmark = when(intent.bookmark) {
                    is TitledPoemBookmark -> intent.bookmark
                    else -> {
                        val poetryBookStore: PoetryBookStore by di.instance()
                        poetryBookStore.getTitledPoemBookmark(intent.bookmark)
                    }
                }
                state.deletePoem(titledPoemBookmark)
            }
            is FavoritesStoreIntent.ApplySwitchPoet -> state
            is FavoritesStoreIntent.SwitchPoet -> state
            is FavoritesStoreIntent.ClearFavorites -> PresentationPoets()
        }.also {
            if(intent.needSave()) { favoritesRepository.saveFavorites(it.toPoetsSequence()) }
        },
        effect = when(intent) {
            is FavoritesStoreIntent.ApplySwitchPoet -> {
                FavoritesStoreEffect.ShowAddConfirmation
            }
            else -> null
        }
    )
}

class FavoritesStore(
    favoritesRepository: FavoritesRepository
): Store<FavoritesStoreState, FavoritesStoreIntent, FavoritesStoreEffect>(
    defaultState = PresentationPoets(value = mapOf()),
    initialiseState = {
        val poetryBookStore: PoetryBookStore by di.instance()
        val favoritesSequence = favoritesRepository.getFavorites()
        println("seq: $favoritesSequence")
        poetryBookStore.getPresentationPoets(favoritesSequence)
    },
    reducer = FavoritesStoreReducer(favoritesRepository = favoritesRepository)
) {
    fun isInFavorites(bookmark: PoemBookmark): Boolean = state.value.isIncludeBookmark(bookmark)
}