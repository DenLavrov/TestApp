package com.test.app.features.profile.presentation.screens.edit_profile

import com.test.app.features.profile.data.models.AvatarData

sealed class EditProfileAction {
    data object Save : EditProfileAction()

    data class Update(val avatar: AvatarData?, val birthday: String?, val about: String, val city: String) :
        EditProfileAction()

    data object DismissError : EditProfileAction()

    data object Init : EditProfileAction()
}