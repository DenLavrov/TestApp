package com.test.app.features.profile.presentation.screens.profile

sealed class ProfileEffect {
    data object Back : ProfileEffect()
}