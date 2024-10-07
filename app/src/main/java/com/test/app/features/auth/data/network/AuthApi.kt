package com.test.app.features.auth.data.network

import com.test.app.features.auth.data.models.AuthCodeResponse
import com.test.app.features.auth.data.models.AuthCodeRequest
import com.test.app.features.auth.data.models.CheckCodeRequest
import com.test.app.features.auth.data.models.AuthResponse
import com.test.app.features.auth.data.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    private companion object {
        const val SEND_AUTH_CODE_URL = "send-auth-code/"
        const val CHECK_AUTH_CODE_REQUEST_URL = "check-auth-code/"
        const val REGISTER_REQUEST_URL = "register/"
    }

    @POST(SEND_AUTH_CODE_URL)
    suspend fun sendAuthCode(@Body request: AuthCodeRequest): AuthCodeResponse

    @POST(CHECK_AUTH_CODE_REQUEST_URL)
    suspend fun checkAuthCode(@Body request: CheckCodeRequest): AuthResponse

    @POST(REGISTER_REQUEST_URL)
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}