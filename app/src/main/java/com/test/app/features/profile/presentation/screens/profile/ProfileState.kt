package com.test.app.features.profile.presentation.screens.profile

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class ProfileState(
    val birthday: String?,
    val userName: String,
    val avatar: String,
    val about: String?,
    @StringRes val zodiac: Int?,
    val phone: String,
    val city: String?,
    val error: String?,
    val isLoading: Boolean
) {
    companion object {
        val empty = ProfileState(
            null,
            "",
            "",
            null,
            null,
            "",
            null,
            null,
            true
        )
    }
}
