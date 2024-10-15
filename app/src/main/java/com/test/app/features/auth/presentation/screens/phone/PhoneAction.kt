package com.test.app.features.auth.presentation.screens.phone

sealed class PhoneAction {
    data class UpdatePhone(val phone: String) : PhoneAction()

    data class UpdateCountry(val countryNumber: String, val countryCode: String) : PhoneAction()

    data object DismissError : PhoneAction()

    data object SendCode : PhoneAction()
}