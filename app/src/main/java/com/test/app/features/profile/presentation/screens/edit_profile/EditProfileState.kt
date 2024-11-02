package com.test.app.features.profile.presentation.screens.edit_profile

import com.test.app.features.profile.domain.models.AvatarData
import kotlinx.serialization.Serializable

@Serializable
data class EditProfileState(
    val username: String,
    val phone: String,
    val name: String,
    val birthday: String?,
    val avatar: AvatarData?,
    val about: String,
    val city: String,
    val error: String?,
    val isLoading: Boolean
) {
    companion object {
        val empty = EditProfileState("", "", "", null, null, "", "", null, false)
    }
}
