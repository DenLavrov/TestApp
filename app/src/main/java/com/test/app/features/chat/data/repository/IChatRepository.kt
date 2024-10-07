package com.test.app.features.chat.data.repository

import com.test.app.features.chat.data.models.Chat
import com.test.app.features.chat.data.models.ChatMessage

interface IChatRepository {
    fun getChats(): List<Chat>
    fun getMessages(id: String): List<ChatMessage>
    fun sendMessage(chatId: String, message: String)
}