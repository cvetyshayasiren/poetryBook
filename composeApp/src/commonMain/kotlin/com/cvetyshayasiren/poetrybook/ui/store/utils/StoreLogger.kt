package com.cvetyshayasiren.poetrybook.ui.store.utils

import co.touchlab.kermit.Logger
import com.cvetyshayasiren.poetrybook.domain.utils.currentTime
import com.cvetyshayasiren.poetrybook.domain.utils.prettyTimeString
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface StoreLogger {
    val tag: String?
        get() = null
    val uuid: String?
        get() = null

    fun logStoreInitialised(storeName: String) {
        sendLog(
            subject = storeName,
            action = "initialised"
        )
    }

    fun logStoreStateInitialised(storeName: String) {
        sendLog(
            subject = storeName,
            action = "initialise state"
        )
    }

    fun logStoreStateResume(storeName: String) {
        sendLog(
            subject = storeName,
            action = "resume state"
        )
    }

    fun logStoreStateCompletion(storeName: String, throwable: Throwable?) {
        val cause = throwable?.cause?.let { " Cause: [${it}]" } ?: ""
        val message = throwable?.message?.let { " Message: [$it]" } ?: ""
        sendLog(
            subject = "$storeName state",
            action = "call on completion.$cause$message",
        )
    }

    fun logStoreEffectStart(storeName: String) {
        sendLog(
            subject = storeName,
            action = "start effect"
        )
    }

    fun logStoreEffectCompletion(storeName: String, throwable: Throwable?) {
        sendLog(
            subject = "$storeName effect",
            action = "call on completion",
        )
    }

    fun logStoreSendIntent(storeName: String, intentName: String) {
        sendLog(
            subject = storeName,
            action = "send intent [$intentName]"
        )
    }

    private fun sendLog(
        subject: String,
        action: String,
        error: String? = null
    ) {
        val time = currentTime().prettyTimeString()
        val uuid = uuid?.let { " (logger UUID: $it)" } ?: ""
        val message = "$time$uuid[$subject] $action"
        val error = error?.let { "and got the error:\n$error" }
        when(error == null) {
            true -> Logger.i(tag ?: "") { message }
            false -> Logger.e(tag ?: "") { "$message\n\n$error" }
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        private fun getRandomUUID() = Uuid.random().toString().takeLast(12)
    }

    class Default(
        override val tag: String? = null,
        override val uuid: String? = getRandomUUID()
    ): StoreLogger
}