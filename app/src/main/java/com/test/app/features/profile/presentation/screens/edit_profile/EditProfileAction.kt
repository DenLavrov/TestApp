package com.test.app.features.profile.presentation.screens.edit_profile

import com.test.app.features.profile.data.models.AvatarData

sealed class EditProfileAction {
    data object Save : EditProfileAction()

    data class UpdateAvatar(val avatar: AvatarData?) : EditProfileAction()

    data class UpdateBirthday(val birthday: String?) : EditProfileAction()

    data class UpdateAbout(val about: String) : EditProfileAction()

    data class UpdateCity(val city: String) : EditProfileAction()

    data object DismissError : EditProfileAction()

    data object Init : EditProfileAction()
}