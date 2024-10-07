package com.test.app.features.auth.presentation.screens.phone

sealed class PhoneAction {
    data class Update(val phone: String, val countryNumber: String, val countryCode: String) : PhoneAction()

    data object DismissError : PhoneAction()

    data object SendCode : PhoneAction()
}