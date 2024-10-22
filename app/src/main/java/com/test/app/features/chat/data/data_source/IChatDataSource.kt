package com.test.app.features.chat.data.data_source

import com.test.app.features.chat.data.models.Chat
import com.test.app.features.chat.data.models.ChatMessage

interface IChatDataSource {
    val chatList: List<Chat>
    val chatMessages: Map<String, List<ChatMessage>>
    fun sendMessage(chatId: String, message: ChatMessage)
}