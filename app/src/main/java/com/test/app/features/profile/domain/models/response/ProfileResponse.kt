package com.test.app.features.profile.domain.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(@SerialName("profile_data") val data: ProfileData)

@Serializable
data class ProfileData(
    val avatars: Avatars?,
    @SerialName("username") val userName: String,
    val birthday: String?,
    val phone: String,
    val city: String?,
    val name: String? = null,
    @SerialName("status") val about: String? = null
)

@Serializable
data class Avatars(val avatar: String)
