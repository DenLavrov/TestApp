package com.test.app.core.data

import com.test.app.core.exceptions.NoConnectionException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

interface IRequester {
    suspend fun makeRequest(request: suspend () -> Unit)
}

class Requester @Inject constructor(private val connectionChecker: ConnectionChecker) : IRequester {
    override suspend fun makeRequest(request: suspend () -> Unit) {
        if (connectionChecker.isConnected.not())
            throw NoConnectionException()
        try {
            request()
        } catch (t: HttpException) {
            val jsonError = t.response()?.errorBody()?.string()?.let { JSONObject(it) }
            val message = jsonError?.getJSONObject("detail")?.getString("message")
            throw Throwable(message)
        }
    }
}