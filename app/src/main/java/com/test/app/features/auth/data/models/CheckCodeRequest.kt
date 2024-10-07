package com.test.app.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CheckCodeRequest(val phone: String, val code: String)
