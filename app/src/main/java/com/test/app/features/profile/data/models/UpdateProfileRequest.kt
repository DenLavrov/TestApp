package com.test.app.features.profile.data.models

import com.test.app.features.profile.domain.models.AvatarData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val username: String,
    val phone: String,
    val name: String,
    val avatar: AvatarData?,
    val birthday: String?,
    val city: String?,
    @SerialName("status") val about: String?
)
