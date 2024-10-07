package com.test.app.features.auth.presentation.screens.code

sealed class CodeAction {
    data class Update(val code: String, val error: String? = null) : CodeAction()

    data class Init(val phone: String) : CodeAction()

    data object DismissError : CodeAction()

    data object Login : CodeAction()
}