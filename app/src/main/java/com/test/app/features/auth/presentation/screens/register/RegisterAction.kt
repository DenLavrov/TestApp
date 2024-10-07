package com.test.app.features.auth.presentation.screens.register

sealed class RegisterAction {
    data class Update(val userName: String, val name: String) : RegisterAction()

    data class Init(val phone: String): RegisterAction()

    data object DismissError : RegisterAction()

    data object Register : RegisterAction()
}