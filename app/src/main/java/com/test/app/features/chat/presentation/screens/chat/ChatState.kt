package com.test.app.features.chat.presentation.screens.chat

import androidx.compose.runtime.Immutable
import com.test.app.features.chat.domain.models.ChatMessage
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class ChatState(
    val id: String,
    @Transient val messages: List<ChatMessage> = emptyList(),
    val text: String
) {
    companion object {
        val empty = ChatState(id = "", text = "")
    }
}
