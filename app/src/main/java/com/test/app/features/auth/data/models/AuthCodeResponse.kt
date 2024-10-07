package com.test.app.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthCodeResponse(@SerialName("is_success") val isSuccess: Boolean)