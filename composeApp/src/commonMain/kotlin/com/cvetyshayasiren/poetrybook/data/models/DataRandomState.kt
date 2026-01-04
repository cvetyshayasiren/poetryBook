package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.models.random.RandomPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.models.random.RandomState
import kotlinx.serialization.Serializable

@Serializable
data class DataRandomState(
    val randomPoemBehaviour: DataRandomPoemBehaviour,
    val isRandomiseSeed: Boolean,
    val isRandomiseIsm: Boolean,
    val isRandomiseThemeMode: Boolean,
) {
    fun toRandomState() = RandomState(
        randomPoemBehaviour = this.randomPoemBehaviour.toRandomPoemBehaviour(),
        isRandomiseSeed = this.isRandomiseSeed,
        isRandomiseIsm = this.isRandomiseIsm,
        isRandomiseThemeMode = this.isRandomiseThemeMode
    )
    companion object {
        fun fromRandomState(state: RandomState): DataRandomState = DataRandomState(
            randomPoemBehaviour = DataRandomPoemBehaviour.fromRandomPoemBehaviour(state.randomPoemBehaviour),
            isRandomiseSeed = state.isRandomiseSeed,
            isRandomiseIsm = state.isRandomiseIsm,
            isRandomiseThemeMode = state.isRandomiseThemeMode
        )
    }
}

@Serializable
sealed interface DataRandomPoemBehaviour {
    data object SamePoet: DataRandomPoemBehaviour
    data object RandomPoet: DataRandomPoemBehaviour

    data class CertainPoet(val poetId: Int): DataRandomPoemBehaviour

    data object FromFavorites: DataRandomPoemBehaviour

    fun toRandomPoemBehaviour(): RandomPoemBehaviour = when(this) {
        is CertainPoet -> RandomPoemBehaviour.CertainPoet(poetId = poetId)
        FromFavorites -> RandomPoemBehaviour.FromFavorites
        RandomPoet -> RandomPoemBehaviour.RandomPoet
        SamePoet -> RandomPoemBehaviour.SamePoet
    }

    companion object {
        fun fromRandomPoemBehaviour(behaviour: RandomPoemBehaviour): DataRandomPoemBehaviour = when(behaviour) {
            is RandomPoemBehaviour.CertainPoet -> CertainPoet(poetId = behaviour.poetId)
            RandomPoemBehaviour.FromFavorites -> FromFavorites
            RandomPoemBehaviour.RandomPoet -> RandomPoet
            RandomPoemBehaviour.SamePoet -> SamePoet
        }
    }
}