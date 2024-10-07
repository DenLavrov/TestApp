package com.test.app.main.presentation

sealed class MainAction {
    data class Update(val isAuthorized: Boolean) : MainAction()
}