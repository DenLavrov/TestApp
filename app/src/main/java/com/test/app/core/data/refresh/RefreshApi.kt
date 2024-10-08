package com.test.app.core.data.refresh

import com.test.app.core.data.refresh.models.RefreshTokenRequest
import com.test.app.core.data.refresh.models.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApi {

    private companion object {
        const val REFRESH_TOKEN_REQUEST_URL = "refresh-token/"
    }

    @POST(REFRESH_TOKEN_REQUEST_URL)
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse
}