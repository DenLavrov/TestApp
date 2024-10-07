package com.test.app.features.auth.presentation.screens.code

import kotlinx.serialization.Serializable

@Serializable
data class CodeState(
    val phone: String,
    val code: String,
    val isValid: Boolean,
    val error: String?,
    val isLoading: Boolean
) {
    companion object {
        val empty = CodeState("", "", true, null, false)
    }
}
