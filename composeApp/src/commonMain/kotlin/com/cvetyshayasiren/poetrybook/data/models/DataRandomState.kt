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
enum class DataRandomPoemBehaviour() {
    SAME_POET, RANDOM_POET, CERTAIN_POET, FROM_FAVORITES;

    fun toRandomPoemBehaviour(): RandomPoemBehaviour = when(this) {
        SAME_POET -> RandomPoemBehaviour.SAME_POET
        RANDOM_POET -> RandomPoemBehaviour.RANDOM_POET
        CERTAIN_POET -> RandomPoemBehaviour.CERTAIN_POET
        FROM_FAVORITES -> RandomPoemBehaviour.FROM_FAVORITES
    }
    companion object {
        fun fromRandomPoemBehaviour(behaviour: RandomPoemBehaviour): DataRandomPoemBehaviour = when(behaviour) {
            RandomPoemBehaviour.SAME_POET -> SAME_POET
            RandomPoemBehaviour.RANDOM_POET -> RANDOM_POET
            RandomPoemBehaviour.CERTAIN_POET -> CERTAIN_POET
            RandomPoemBehaviour.FROM_FAVORITES -> FROM_FAVORITES
        }
    }
}