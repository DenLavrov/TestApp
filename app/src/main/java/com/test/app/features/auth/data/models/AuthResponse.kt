package com.test.app.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token") val token: String?,
    @SerialName("refresh_token") val refreshToken: String?,
    @SerialName("is_user_exists") val isUserExists: Boolean
)