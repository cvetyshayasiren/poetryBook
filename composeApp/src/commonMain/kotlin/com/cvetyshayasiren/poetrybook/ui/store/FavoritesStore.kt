package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.BasicSequence
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Bookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.Sequence
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.add
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.delete
import com.cvetyshayasiren.poetrybook.domain.models.poet.*
import com.cvetyshayasiren.poetrybook.domain.repository.FavoritesRepository
import com.cvetyshayasiren.poetrybook.domain.utils.toLinkedHashSet
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import org.kodein.di.instance
import kotlin.text.get

typealias FavoritesStoreState = Sequence<FavoritePoetBookmark, FavoriteBookmark>

sealed interface FavoritesStoreIntent {
    data class AddPoem(val bookmark: Bookmark): FavoritesStoreIntent
    data class DeletePoem(val bookmark: Bookmark): FavoritesStoreIntent

    data class ApplySwitchPoet(val poetBookmark: FavoritePoetBookmark): FavoritesStoreIntent
    data class AddPoet(val poetBookmark: PoetBookmark): FavoritesStoreIntent
    data class DeletePoet(val poetBookmark: PoetBookmark): FavoritesStoreIntent

    data object ClearFavorites: FavoritesStoreIntent

    fun needSave(): Boolean = this !is ApplySwitchPoet
}

sealed interface FavoritesStoreEffect {
    data class ShowAddConfirmation(val count: Int): FavoritesStoreEffect
    data class ShowDeleteConfirmation(val count: Int): FavoritesStoreEffect
}

class FavoritesStoreReducer(
    val favoritesRepository: FavoritesRepository
): Reducer<FavoritesStoreState, FavoritesStoreIntent, FavoritesStoreEffect> {
    override suspend fun reduce(
        state: FavoritesStoreState,
        intent: FavoritesStoreIntent
    ): ReducerResult<FavoritesStoreState, out FavoritesStoreEffect?> = ReducerResult.build(
        state = when(intent) {
            is FavoritesStoreIntent.AddPoem -> state.addPoem(intent.bookmark)
            is FavoritesStoreIntent.DeletePoem ->  state.deletePoem(intent.bookmark)
            is FavoritesStoreIntent.ApplySwitchPoet -> state
            is FavoritesStoreIntent.AddPoet -> state.addPoet(intent.poetBookmark)
            is FavoritesStoreIntent.DeletePoet -> state.deletePoet(intent.poetBookmark)
            is FavoritesStoreIntent.ClearFavorites -> FavoritesStoreState(mapOf())
        }.also {
            if(intent.needSave()) { favoritesRepository.saveFavorites(it) }
        },
        effect = when(intent) {
            is FavoritesStoreIntent.ApplySwitchPoet -> {
                val poemsInFavoritesSize = state.value[intent.poetBookmark]?.size ?: 0
                when(intent.poetBookmark.isInFavorites) {
                    true -> FavoritesStoreEffect.ShowDeleteConfirmation(poemsInFavoritesSize)
                    false -> {
                        val poetryBookStore: PoetryBookStore by di.instance()
                        val book = poetryBookStore.getBook()
                        val poemsSize = book.getPoemsSize(intent.poetBookmark)
                        FavoritesStoreEffect.ShowAddConfirmation(poemsSize - poemsInFavoritesSize)
                    }
                }
            }
            else -> null
        }
    )
}

class FavoritesStore(
    favoritesRepository: FavoritesRepository
): Store<FavoritesStoreState, FavoritesStoreIntent, FavoritesStoreEffect>(
    defaultState = FavoritesStoreState(mapOf()),
    initialiseState = { favoritesRepository.getFavorites().toFavoritesStoreState() },
    reducer = FavoritesStoreReducer(favoritesRepository)
)

//models
data class FavoritePoetBookmark(
    override val id: Int,
    val name: String,
    val isInFavorites: Boolean
): PoetBookmark

data class FavoriteBookmark(
    override val poetId: Int,
    override val poemId: Int,
    val title: String
): Bookmark

suspend fun Sequence<out PoetBookmark, out Bookmark>.toFavoritesStoreState(): FavoritesStoreState {
    val poetryBookStore: PoetryBookStore by di.instance()
    val book = poetryBookStore.getBook()
    return FavoritesStoreState(
        value = buildMap {
            value.forEach { (poetBookmark, poemBookmarks) ->
                val poemsSize = book.getPoemsSize(poetBookmark)
                val isInFavorites = poemsSize == poemBookmarks.size

                set(
                    key = FavoritePoetBookmark(
                        id = poetBookmark.id,
                        name = book.getPoetName(poetBookmark),
                        isInFavorites = isInFavorites
                    ),
                    value = poemBookmarks.map { poemBookmark ->
                        val poem = book.getPoem(poemBookmark)
                        FavoriteBookmark(
                            poetId = poemBookmark.poetId,
                            poemId = poemBookmark.poemId,
                            title = poem.title
                        )
                    }.toLinkedHashSet()
                )
            }
        }
    )
}

suspend fun FavoritesStoreState.addPoem(bookmark: Bookmark): FavoritesStoreState {
    val basicSequence = this.toBasicSequence()
    val basicBookmark = bookmark.toBasicPoemBookmark()
    return basicSequence.add(basicBookmark).toFavoritesStoreState()
}

suspend fun FavoritesStoreState.deletePoem(bookmark: Bookmark): FavoritesStoreState {
    val basicSequence = this.toBasicSequence()
    val basicBookmark = bookmark.toBasicPoemBookmark()
    return basicSequence.delete(basicBookmark).toFavoritesStoreState()
}

suspend fun FavoritesStoreState.addPoet(bookmark: PoetBookmark): FavoritesStoreState {
    val poetryBookStore: PoetryBookStore by di.instance()
    val book = poetryBookStore.getBook()
    val basicSequenceMap = this.toBasicSequence().value.toMutableMap()
    val bookmarks = book[bookmark.id].poems.map { Bookmark.Basic(poetId = it.poetId, poemId = it.id) }
    basicSequenceMap[bookmark.toBasicPoetBookmark()] = bookmarks.toLinkedHashSet()
    return BasicSequence(value = basicSequenceMap).toFavoritesStoreState()
}

suspend fun FavoritesStoreState.deletePoet(bookmark: PoetBookmark): FavoritesStoreState {
    val basicSequenceMap = this.toBasicSequence().value
    return BasicSequence(value = basicSequenceMap.minus(key = bookmark.toBasicPoetBookmark())).toFavoritesStoreState()
}






