package com.test.app.features.chat.presentation.screens.chat

sealed class ChatAction {
    data class Init(val id: String) : ChatAction()
    data class UpdateText(val text: String) : ChatAction()
    data class Send(val text: String) : ChatAction()
}