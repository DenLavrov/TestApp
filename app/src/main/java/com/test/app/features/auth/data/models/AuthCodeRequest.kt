package com.test.app.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthCodeRequest(val phone: String)
