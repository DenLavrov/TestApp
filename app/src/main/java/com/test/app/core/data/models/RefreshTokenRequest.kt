package com.test.app.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(@SerialName("refresh_token") val refreshToken: String)
