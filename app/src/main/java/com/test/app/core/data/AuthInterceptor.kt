package com.test.app.core.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val storage: Storage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = storage.getString(Storage.TOKEN_KEY)
        if (token.isNullOrEmpty()) {
            return chain.proceed(chain.request())
        }
        val builder = chain.request().newBuilder()
        builder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(builder.build())
    }
}