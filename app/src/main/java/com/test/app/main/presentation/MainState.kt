package com.test.app.main.presentation

import kotlinx.serialization.Serializable

@Serializable
data class MainState(val isAuthorized: Boolean, val title: Int?) {
    companion object {
        val Empty = MainState(false, null)
    }
}
