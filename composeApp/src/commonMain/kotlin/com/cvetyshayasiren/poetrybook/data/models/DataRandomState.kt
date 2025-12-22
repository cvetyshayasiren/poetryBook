package com.cvetyshayasiren.poetrybook.data.models

import com.cvetyshayasiren.poetrybook.domain.NextPoemBehaviour
import com.cvetyshayasiren.poetrybook.domain.RandomState
import kotlinx.serialization.Serializable

@Serializable
data class DataRandomState(
    val nextPoemBehaviour: DataNextPoemBehaviour,
    val isRandomiseSeed: Boolean,
    val isRandomiseIsm: Boolean,
    val isRandomiseThemeMode: Boolean,
) {
    fun toRandomState() = RandomState(
        nextPoemBehaviour = this.nextPoemBehaviour.toNextPoemBehaviour(),
        isRandomiseSeed = this.isRandomiseSeed,
        isRandomiseIsm = this.isRandomiseIsm,
        isRandomiseThemeMode = this.isRandomiseThemeMode
    )
    companion object {
        fun fromRandomState(state: RandomState): DataRandomState = DataRandomState(
            nextPoemBehaviour = DataNextPoemBehaviour.fromNextPoemBehaviour(state.nextPoemBehaviour),
            isRandomiseSeed = state.isRandomiseSeed,
            isRandomiseIsm = state.isRandomiseIsm,
            isRandomiseThemeMode = state.isRandomiseThemeMode
        )
    }
}

@Serializable
enum class DataNextPoemBehaviour() {
    SAME_POET, RANDOM_POET, CERTAIN_POET, FROM_FAVORITES;

    fun toNextPoemBehaviour(): NextPoemBehaviour = when(this) {
        SAME_POET -> NextPoemBehaviour.SAME_POET
        RANDOM_POET -> NextPoemBehaviour.RANDOM_POET
        CERTAIN_POET -> NextPoemBehaviour.CERTAIN_POET
        FROM_FAVORITES -> NextPoemBehaviour.FROM_FAVORITES
    }
    companion object {
        fun fromNextPoemBehaviour(behaviour: NextPoemBehaviour): DataNextPoemBehaviour = when(behaviour) {
            NextPoemBehaviour.SAME_POET -> SAME_POET
            NextPoemBehaviour.RANDOM_POET -> RANDOM_POET
            NextPoemBehaviour.CERTAIN_POET -> CERTAIN_POET
            NextPoemBehaviour.FROM_FAVORITES -> FROM_FAVORITES
        }
    }
}