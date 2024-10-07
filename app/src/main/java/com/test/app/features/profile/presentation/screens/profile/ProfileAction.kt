package com.test.app.features.profile.presentation.screens.profile

sealed class ProfileAction {
    data object Init : ProfileAction()

    data object DismissError : ProfileAction()
}