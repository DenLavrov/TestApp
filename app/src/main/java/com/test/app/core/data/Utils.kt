package com.test.app.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.json.JSONObject
import retrofit2.HttpException

fun <T> Flow<T>.handleError() = catch {
    if (it is HttpException) {
        val jsonError = it.response()?.errorBody()?.string()?.let { body -> JSONObject(body) }
        val message = jsonError?.getJSONObject("detail")?.getString("message")
        throw Throwable(message)
    }
    throw it
}