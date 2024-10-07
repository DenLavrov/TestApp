package com.test.app.features.profile.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(@SerialName("profile_data") val data: ProfileData)

@Serializable
data class ProfileData(
    val avatar: String?,
    @SerialName("username") val userName: String,
    val birthday: String?,
    val phone: String,
    val city: String?,
    val name: String? = null,
    @SerialName("status") val about: String? = null
)
