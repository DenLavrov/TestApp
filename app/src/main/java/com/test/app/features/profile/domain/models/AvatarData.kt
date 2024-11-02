package com.test.app.features.profile.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarData(
    val filename: String,
    @SerialName("base_64") val base64: String
)