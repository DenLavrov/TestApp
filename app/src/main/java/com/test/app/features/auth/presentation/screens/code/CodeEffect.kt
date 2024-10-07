package com.test.app.features.auth.presentation.screens.code

sealed class CodeEffect {
    data object Register : CodeEffect()
}