package com.test.app.features.auth.presentation.screens.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterState(
    val userName: String,
    val name: String,
    val phone: String,
    val isUserNameValid: Boolean,
    val isNameValid: Boolean,
    val error: String?,
    val isLoading: Boolean
) {
    companion object {
        val empty = RegisterState("", "", "", true, isNameValid = true, null, false)
    }
}