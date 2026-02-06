package com.cvetyshayasiren.poetrybook.ui.store.utils

import kotlin.jvm.JvmInline

@JvmInline
value class ReducerResult<S, E>(val value: Triple<S, E?, (suspend () -> Unit)?>) {
    fun state() = this.value.first
    fun effect() = this.value.second
    fun sideEffect() = this.value.third

    companion object {
        fun <S, E>make(state: S, effect: E? = null, sideEffect: (suspend () -> Unit)? = null): ReducerResult<S, E?> =
            ReducerResult(value = Triple(first = state, second = effect, third = sideEffect))

        class Builder<S, E> {
            var newState: S? = null
            var effect: E? = null
            var sideEffect: (suspend () -> Unit)? = null

            fun build(): ReducerResult<S, E> {
                require(newState != null) { "State cannot be null." }
                return ReducerResult(value = Triple(first = newState!!, second = effect, third = sideEffect))
            }
        }

        suspend fun <S, E>build(block: suspend Builder<S, E>.() -> Unit): ReducerResult<S, E> {
            val builder = Builder<S, E>()
            builder.block()
            return builder.build()
        }
    }
}

interface Reducer<S, in I, out E> {
    suspend fun reduce(state: S, intent: I): ReducerResult<S, out E?>
}