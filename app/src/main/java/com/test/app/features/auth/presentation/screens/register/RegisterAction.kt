package com.test.app.features.auth.presentation.screens.register

sealed class RegisterAction {
    data class UpdateUserName(val userName: String) : RegisterAction()

    data class UpdateName(val name: String) : RegisterAction()

    data class Init(val phone: String): RegisterAction()

    data object DismissError : RegisterAction()

    data object Register : RegisterAction()
}