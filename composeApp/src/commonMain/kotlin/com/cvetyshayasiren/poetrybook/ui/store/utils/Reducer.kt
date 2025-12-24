package com.cvetyshayasiren.poetrybook.ui.store.utils

import kotlin.jvm.JvmInline

@JvmInline
value class ReducerResult<S, E>(val value: Pair<S, E?>) {
    fun state() = this.value.first
    fun effect() = this.value.second

    companion object {
        fun <S, E>build(state: S, effect: E? = null): ReducerResult<S, E?> =
            ReducerResult(value = Pair(first = state, second = effect))
    }
}
interface Reducer<S, in I, out E> {
    suspend fun reduce(state: S, intent: I): ReducerResult<S, out E?>
}