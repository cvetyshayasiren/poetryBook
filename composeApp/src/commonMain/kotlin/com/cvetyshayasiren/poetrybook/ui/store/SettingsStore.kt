package com.cvetyshayasiren.poetrybook.ui.store

import com.cvetyshayasiren.poetrybook.di.di
import com.cvetyshayasiren.poetrybook.domain.repository.SettingsRepository
import com.cvetyshayasiren.poetrybook.ui.store.utils.Reducer
import com.cvetyshayasiren.poetrybook.ui.store.utils.ReducerResult
import com.cvetyshayasiren.poetrybook.ui.store.utils.Store
import org.kodein.di.instance

data object SettingsStoreState

sealed interface SettingsStoreIntent {
    data object SetDefault: SettingsStoreIntent
    data object ApplyWipe: SettingsStoreIntent
    data object Wipe: SettingsStoreIntent
}

sealed interface SettingsStoreEffect {
    data object ShowWipeConfirmation: SettingsStoreEffect
}

class SettingsStoreReducer(val repository: SettingsRepository):
    Reducer<SettingsStoreState, SettingsStoreIntent, SettingsStoreEffect> {

        override suspend fun reduce(
            state: SettingsStoreState,
            intent: SettingsStoreIntent
        ): ReducerResult<SettingsStoreState, out SettingsStoreEffect?> = ReducerResult.build {
            newState = state
            effect = when(intent) {
                SettingsStoreIntent.ApplyWipe -> SettingsStoreEffect.ShowWipeConfirmation
                else -> null
            }
            sideEffect = {
                val setDefault = {
                    val styleStore: StyleStore by di.instance()
                    val randomStore: RandomStore by di.instance()
                    styleStore.sendIntent(StyleStoreIntent.SetDefault)
                    randomStore.sendIntent(RandomStoreIntent.SetDefault)
                }

                when(intent) {
                    SettingsStoreIntent.SetDefault -> {
                        setDefault()
                    }
                    SettingsStoreIntent.Wipe -> {
                        setDefault()
                        val favoritesStore: FavoritesStore by di.instance()
                        val historyStore: HistoryStore by di.instance()
                        favoritesStore.sendIntent(FavoritesStoreIntent.ClearFavorites)
                        historyStore.sendIntent(HistoryStoreIntent.ClearAll)
                        repository.wipe()
                    }
                    else -> { }
                }
            }
        }
}

class SettingsStore(
    repository: SettingsRepository
): Store<SettingsStoreState, SettingsStoreIntent, SettingsStoreEffect>(
    defaultState = SettingsStoreState,
    reducer = SettingsStoreReducer(repository)
)