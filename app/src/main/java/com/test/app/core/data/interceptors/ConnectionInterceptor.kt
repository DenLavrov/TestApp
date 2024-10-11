package com.test.app.core.data.interceptors

import com.test.app.core.data.ConnectionChecker
import com.test.app.core.exceptions.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectionInterceptor @Inject constructor(private val connectionChecker: ConnectionChecker) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (connectionChecker.isConnected.not())
            throw NoConnectionException()
        return chain.proceed(chain.request())
    }
}