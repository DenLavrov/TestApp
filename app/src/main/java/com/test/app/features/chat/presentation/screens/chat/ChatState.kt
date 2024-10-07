package com.test.app.features.chat.presentation.screens.chat

import com.test.app.features.chat.data.models.ChatMessage
import kotlinx.serialization.Serializable

@Serializable
data class ChatState(val id: String, val messages: List<ChatMessage>, val text: String) {
    companion object {
        val empty = ChatState("", emptyList(), "")
    }
}
