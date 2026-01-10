package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.poem.TitledPoemBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.*
import com.cvetyshayasiren.poetrybook.domain.repository.FavoritesRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import org.kodein.di.instance

typealias FavoritesStoreState = PresentationPoets

sealed interface FavoritesStoreIntent {
    class AddPoem(val bookmark: TitledPoemBookmark): FavoritesStoreIntent
    class DeletePoem(val bookmark: TitledPoemBookmark): FavoritesStoreIntent

    data object ApplyAddPoet: FavoritesStoreIntent
    class AddPoet(val poetId: Int): FavoritesStoreIntent

    data object ApplyDeletePoet: FavoritesStoreIntent
    class DeletePoet(val poetId: Int): FavoritesStoreIntent

}

sealed interface FavoritesStoreEffect {
    data object ShowAddConfirmation: FavoritesStoreEffect
    data object ShowDeleteConfirmation: FavoritesStoreEffect
}

class FavoritesStoreReducer(): Reducer<FavoritesStoreState, FavoritesStoreIntent, FavoritesStoreEffect> {
    override suspend fun reduce(
        state: FavoritesStoreState,
        intent: FavoritesStoreIntent
    ): ReducerResult<FavoritesStoreState, out FavoritesStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is FavoritesStoreIntent.AddPoem -> state.addPoem(intent.bookmark)
            is FavoritesStoreIntent.DeletePoem ->  state.deletePoem(intent.bookmark)
            is FavoritesStoreIntent.DeletePoet ->  state.deletePoet(intent.poetId)
            is FavoritesStoreIntent.ApplyAddPoet -> state
            is FavoritesStoreIntent.ApplyDeletePoet -> state
            is FavoritesStoreIntent.AddPoet -> {
                val poetryBookStore: PoetryBookStore by di.instance()
                val separatedPoet = poetryBookStore.getSeparatedPresentationPoets(intent.poetId)
                state.addPoet(separatedPoet)
            }
        },
        effect = when(intent) {
            FavoritesStoreIntent.ApplyAddPoet -> FavoritesStoreEffect.ShowAddConfirmation
            FavoritesStoreIntent.ApplyDeletePoet -> FavoritesStoreEffect.ShowDeleteConfirmation
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
        poetryBookStore.getPresentationPoets(favoritesSequence)
    },
    reducer = FavoritesStoreReducer()
)