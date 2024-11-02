package com.test.app.features.chat.presentation.screens.chats

import androidx.compose.runtime.Immutable
import com.test.app.features.chat.domain.models.Chat
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Immutable
@Serializable
data class ChatsState(@Transient val data: List<Chat> = emptyList(), val avatar: String?) {
    companion object {
        val empty = ChatsState(avatar = null)
    }
}
