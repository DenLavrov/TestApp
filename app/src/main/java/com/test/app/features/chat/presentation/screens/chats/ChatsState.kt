package com.test.app.features.chat.presentation.screens.chats

import androidx.compose.runtime.Immutable
import com.test.app.features.chat.data.models.Chat
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChatsState(val data: List<Chat>, val avatar: String?) {
    companion object {
        val empty = ChatsState(emptyList(), null)
    }
}
