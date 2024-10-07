package com.test.app.features.profile.data.network

import com.test.app.features.profile.data.models.ProfileResponse
import com.test.app.features.profile.data.models.UpdateProfileRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {
    private companion object {
        const val PROFILE_URL = "me/"
    }

    @GET(PROFILE_URL)
    suspend fun getProfile(): ProfileResponse

    @PUT(PROFILE_URL)
    suspend fun updateProfile(@Body request: UpdateProfileRequest)
}