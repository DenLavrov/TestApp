package com.test.app.features.chat.data.repository

import com.test.app.core.data.Dispatchers
import com.test.app.features.chat.data.data_source.IChatDataSource
import com.test.app.features.chat.data.models.ChatMessage
import com.test.app.features.chat.domain.repository.IChatRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.random.Random

class ChatRepository @Inject constructor(
    private val chatDataSource: IChatDataSource,
    private val dispatchers: Dispatchers
) : IChatRepository {
    override fun getChats() = chatDataSource.chatList

    override fun getMessages(id: String) = flow {
        emit(chatDataSource.chatMessages[id]!!)
    }.flowOn(dispatchers.default)

    override fun sendMessage(chatId: String, message: String) = flow {
        emit(
            chatDataSource.sendMessage(
                chatId, ChatMessage(
                    Random.nextInt(),
                    message,
                    ChatMessage.Type.OUTGOING,
                    OffsetDateTime.now()
                )
            )
        )
    }.flowOn(dispatchers.default)
}