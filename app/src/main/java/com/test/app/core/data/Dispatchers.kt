package com.test.app.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Dispatchers @Inject constructor() {
    val io
        get() = Dispatchers.IO

    val default
        get() = Dispatchers.Default

    suspend inline fun <T> withIo(crossinline block: suspend CoroutineScope.() -> T) =
        withContext(io) {
            block()
        }

    suspend inline fun <T> withDefault(crossinline block: suspend CoroutineScope.() -> T) =
        withContext(default) {
            block()
        }
}

