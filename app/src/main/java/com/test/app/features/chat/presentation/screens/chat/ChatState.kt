package com.test.app.features.chat.presentation.screens.chat

import androidx.compose.runtime.Immutable
import com.test.app.features.chat.data.models.ChatMessage
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ChatState(val id: String, val messages: List<ChatMessage>, val text: String) {
    companion object {
        val empty = ChatState("", emptyList(), "")
    }
}
