package com.test.app.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val phone: String,
    val name: String,
    @SerialName("username") val userName: String
)
