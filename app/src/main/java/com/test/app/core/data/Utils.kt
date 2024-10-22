package com.test.app.core.data

import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

suspend inline fun <T> runHttpRequest(
    dispatchers: Dispatchers,
    crossinline action: suspend () -> T
) = withContext(dispatchers.io) {
    try {
        action()
    } catch (t: HttpException) {
        val jsonError = t.response()?.errorBody()?.string()?.let { body -> JSONObject(body) }
        val message = jsonError?.getJSONObject("detail")?.getString("message")
        throw Throwable(message)
    }
}