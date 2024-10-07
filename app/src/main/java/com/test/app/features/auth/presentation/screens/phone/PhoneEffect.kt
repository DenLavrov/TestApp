package com.test.app.features.auth.presentation.screens.phone

sealed class PhoneEffect {
    data class CodeSent(val phone: String) : PhoneEffect()
}