package com.test.app.features.chat.domain.repository

import com.test.app.features.chat.domain.models.Chat
import com.test.app.features.chat.domain.models.ChatMessage

interface IChatRepository {
    fun getChats(): List<Chat>
    suspend fun getMessages(id: String): List<ChatMessage>
    suspend fun sendMessage(chatId: String, message: String)
}