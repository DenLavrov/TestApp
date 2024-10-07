package com.test.app.features.auth.presentation.screens.phone

import kotlinx.serialization.Serializable

@Serializable
data class PhoneState(
    val countryCode: String,
    val countryNumber: String,
    val phone: String,
    val isValid: Boolean,
    val error: String?,
    val isLoading: Boolean
) {
    companion object {
        val empty = PhoneState("", "", "", true, null, false)
    }
}
