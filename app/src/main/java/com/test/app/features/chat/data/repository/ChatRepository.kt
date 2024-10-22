package com.test.app.features.chat.data.repository

import com.test.app.core.data.Dispatchers
import com.test.app.features.chat.data.data_source.IChatDataSource
import com.test.app.features.chat.data.models.ChatMessage
import com.test.app.features.chat.domain.repository.IChatRepository
import java.time.OffsetDateTime
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatDataSource: IChatDataSource,
    private val dispatchers: Dispatchers
) : IChatRepository {
    override fun getChats() = chatDataSource.chatList

    override suspend fun getMessages(id: String) = dispatchers.withDefault {
        chatDataSource.chatMessages[id]!!
    }

    override suspend fun sendMessage(chatId: String, message: String) = dispatchers.withDefault {
        chatDataSource.sendMessage(
            chatId, ChatMessage(
                ThreadLocalRandom.current().nextInt(),
                message,
                ChatMessage.Type.OUTGOING,
                OffsetDateTime.now()
            )
        )
    }
}