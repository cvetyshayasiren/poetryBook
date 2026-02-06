package com.cvetyshayasiren.poetrybook.ui.store

import androidx.lifecycle.viewModelScope
import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.models.bookmark.PoetBookmark
import com.cvetyshayasiren.poetrybook.domain.models.poet.getPoetName
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import com.cvetyshayasiren.poetrybook.domain.repository.HistoryRepository
import com.cvetyshayasiren.poetrybook.domain.repository.RandomStateRepository
import com.cvetyshayasiren.poetrybook.ui.store.BundleBookmark.Type
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import kotlinx.coroutines.launch
import org.kodein.di.instance
import kotlin.getValue

data class RandomStoreState(
    val randomState: RandomState,
    val poetsBundle: PoetsBundle
) {
    fun favoritesIsNotEmpty(): Boolean = poetsBundle.favoritePoets.isNotEmpty()
}

sealed interface RandomStoreIntent {
    class SetNextPoemBehaviour(val nextPoemBehaviour: RandomPoemBehaviour): RandomStoreIntent
    class SetIsRandomiseSeed(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseIsm(val value: Boolean): RandomStoreIntent
    class SetIsRandomiseThemeMod(val value: Boolean): RandomStoreIntent
}

sealed interface RandomStoreEffect

class RandomStoreReducer(): Reducer<RandomStoreState, RandomStoreIntent, RandomStoreEffect> {
    override suspend fun reduce(
        state: RandomStoreState,
        intent: RandomStoreIntent
    ): ReducerResult<RandomStoreState, out RandomStoreEffect?> = ReducerResult.build {
        newState = when(intent) {
            is RandomStoreIntent.SetIsRandomiseIsm ->
                state.copy(randomState = state.randomState.copy(isRandomiseIsm = intent.value))
            is RandomStoreIntent.SetIsRandomiseSeed ->
                state.copy(randomState = state.randomState.copy(isRandomiseSeed = intent.value))
            is RandomStoreIntent.SetIsRandomiseThemeMod ->
                state.copy(randomState = state.randomState.copy(isRandomiseThemeMode = intent.value))
            is RandomStoreIntent.SetNextPoemBehaviour ->
                state.copy(
                    randomState = state.randomState.copy(randomPoemBehaviour = intent.nextPoemBehaviour),
                    poetsBundle = state.poetsBundle.copy(
                        option = PoetsBundle.getCurrentOption(randomPoemBehaviour = intent.nextPoemBehaviour)
                    )
                )
        }
    }
}


class RandomStore(
    repository: RandomStateRepository
): Store<RandomStoreState, RandomStoreIntent, RandomStoreEffect>(
    defaultState = RandomStoreState(
        randomState = RandomState(),
        poetsBundle = PoetsBundle()
    ),
    initialiseState = {
        val randomState = repository.getRandomState()
        RandomStoreState(
            randomState = randomState,
            poetsBundle = PoetsBundle.getBundle(randomState.randomPoemBehaviour)
        )
    },
    reducer = RandomStoreReducer()
) {
    init {
        launchAfterInit { savingDaemon(repository) }
        launchAfterInit { pageDaemon() }
        launchAfterInit { favoritesDaemon() }
        launchAfterInit { historyDaemon() }
    }

    suspend fun savingDaemon(repository: RandomStateRepository) {
        state.collect { randomStoreState ->
            if(stateIsInit) { repository.saveRandomState(randomStoreState.randomState) }
        }
    }

    suspend fun pageDaemon() {
        val pageStore: PageStore by di.instance()
        pageStore.state.collect { pageStoreState ->
            updateState { oldState ->
                oldState.refreshBundlePage(pageStoreState = pageStoreState)
            }
        }
    }

    suspend fun favoritesDaemon() {
        val favoritesStore: FavoritesStore by di.instance()
        favoritesStore.state.collect { favoritesStoreState ->
            updateState { oldState ->
                oldState.refreshBundleFavorites(favoritesStoreState = favoritesStoreState)
            }
        }
    }

    suspend fun historyDaemon() {
        val historyStore: HistoryStore by di.instance()
        historyStore.state.collect { historyStoreState ->
            updateState { oldState ->
                oldState.refreshBundleHistory(historyStoreState = historyStoreState)
            }
        }
    }
}

//models

data class PoetsBundle(
    val option: BundleBookmark? = null,
    val currentPoet: BundleBookmark? = null,
    val favoritePoets: BundleBookmarks = listOf(),
    val historyPoets: BundleBookmarks = listOf(),
    val otherPoets: BundleBookmarks = listOf(),
) {
    fun fullList(): BundleBookmarks {
        val optionList = if(option != null) listOf(option) else emptyList()
        val currentPoetList = if(currentPoet != null) listOf(currentPoet) else emptyList()
        return optionList + currentPoetList + favoritePoets + historyPoets + otherPoets
    }

    fun first(): BundleBookmark =
        option ?: currentPoet ?: favoritePoets.firstOrNull() ?: historyPoets.firstOrNull() ?: otherPoets.first()

    fun option(): String = option?.name ?: "..."

    companion object {
        suspend fun getBundle(randomPoemBehaviour: RandomPoemBehaviour): PoetsBundle =
            PoetsBundle(
                option = getCurrentOption(randomPoemBehaviour),
                currentPoet = getCurrentPoet(),
                favoritePoets = getFavoritePoets(),
                historyPoets = getHistoryPoets(),
                otherPoets = getOtherPoets()
            )

        suspend fun getCurrentOption(randomPoemBehaviour: RandomPoemBehaviour): BundleBookmark? =
            when(randomPoemBehaviour is RandomPoemBehaviour.CertainPoet) {
                true -> {
                    val poetryBookStore: PoetryBookStore by di.instance()
                    val book = poetryBookStore.getBook()
                    val poetBookmark = randomPoemBehaviour.poetBookmark
                    BundleBookmark(
                        id = poetBookmark.id,
                        name = book.getPoetName(poetBookmark),
                        type = Type.OPTION
                    )
                }
                false -> null
            }

        fun getCurrentPoet(pageStoreState: PageStoreState? = null): BundleBookmark? = run {
            val pageStoreState = pageStoreState ?: run {
                val pageStore: PageStore by di.instance()
                pageStore.state.value
            }
            when(pageStoreState) {
                PageStoreState.Loading -> null
                is PageStoreState.Prepared -> BundleBookmark(
                    id = pageStoreState.page.poetId,
                    name = pageStoreState.page.poetName,
                    type = Type.CURRENT
                )
            }
        }

        fun getFavoritePoets(favoritesStoreState: FavoritesStoreState? = null): BundleBookmarks = run {
            val favoritesStoreState = favoritesStoreState ?: run {
                val favoritesStore: FavoritesStore by di.instance()
                favoritesStore.state.value
            }
            favoritesStoreState.value.entries
                .sortedWith(comparator = compareByDescending { it.value.count() })
                .map {
                    BundleBookmark(
                        id = it.key.id,
                        name = it.key.name,
                        type = Type.FAVORITE
                    )
                }
        }

        fun getHistoryPoets(historyStoreState: HistoryStoreState? = null): BundleBookmarks = run {
            val historyStoreState = historyStoreState ?: run {
                val historyStore: HistoryStore by di.instance()
                historyStore.state.value
            }
            historyStoreState
                .toHashSet()
                .map {
                    BundleBookmark(
                        id = it.poetId,
                        name = it.poetName,
                        type = Type.HISTORY
                    )
                }
                .takeLast(5)
        }

        private suspend fun getOtherPoets(): BundleBookmarks = run {
            val poetryBookStore: PoetryBookStore by di.instance()
            val book = poetryBookStore.getBook()
            book.map { poet -> BundleBookmark(id = poet.id, name = poet.name, type = Type.OTHER) }
        }
    }
}

data class BundleBookmark(
    override val id: Int,
    val name: String,
    val type: Type
): PoetBookmark {
    enum class Type { OPTION, CURRENT, FAVORITE, HISTORY, OTHER }
}

typealias BundleBookmarks = List<BundleBookmark>

fun RandomStoreState.refreshBundleFavorites(
    favoritesStoreState: FavoritesStoreState? = null
): RandomStoreState = copy(
    poetsBundle = this.poetsBundle.copy(
        favoritePoets = PoetsBundle.getFavoritePoets(favoritesStoreState)
    )
)

fun RandomStoreState.refreshBundleHistory(
    historyStoreState: HistoryStoreState? = null
): RandomStoreState = copy(
    poetsBundle = this.poetsBundle.copy(
        historyPoets = PoetsBundle.getHistoryPoets(historyStoreState)
    )
)

fun RandomStoreState.refreshBundlePage(
    pageStoreState: PageStoreState? = null,
    historyStoreState: HistoryStoreState? = null
): RandomStoreState = copy(
    poetsBundle = this.poetsBundle.copy(
        currentPoet = PoetsBundle.getCurrentPoet(pageStoreState),
        historyPoets = PoetsBundle.getHistoryPoets(historyStoreState)
    )
)