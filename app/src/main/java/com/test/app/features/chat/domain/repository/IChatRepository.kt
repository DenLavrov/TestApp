package com.test.app.features.chat.domain.repository

import com.test.app.features.chat.data.models.Chat
import com.test.app.features.chat.data.models.ChatMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    fun getChats(): List<Chat>
    suspend fun getMessages(id: String): List<ChatMessage>
    suspend fun sendMessage(chatId: String, message: String)
}