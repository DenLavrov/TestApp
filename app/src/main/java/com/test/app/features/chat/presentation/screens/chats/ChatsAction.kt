package com.test.app.features.chat.presentation.screens.chats

sealed class ChatsAction {
    data object Init : ChatsAction()
}