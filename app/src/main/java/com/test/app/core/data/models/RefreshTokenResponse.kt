package com.test.app.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("access_token") val token: String
) {
    companion object {
        val empty = RefreshTokenResponse("", "")
    }
}
