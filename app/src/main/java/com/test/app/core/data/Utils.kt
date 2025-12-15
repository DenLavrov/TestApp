package com.test.app.core.data

import org.json.JSONObject
import retrofit2.HttpException

suspend inline fun <T> runHttpRequest(
    crossinline action: suspend () -> T
) = try {
        action()
    } catch (t: HttpException) {
        val jsonError = t.response()?.errorBody()?.string()?.let { body -> JSONObject(body) }
        val message = jsonError?.getJSONObject("detail")?.getString("message")
        throw Throwable(message)
    }