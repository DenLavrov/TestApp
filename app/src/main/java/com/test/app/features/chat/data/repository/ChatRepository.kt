package com.test.app.features.chat.data.repository

import com.test.app.features.chat.data.models.Chat
import com.test.app.features.chat.data.models.ChatMessage
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class ChatRepository @Inject constructor() : IChatRepository {
    private val chatList =
        listOf(
            Chat(
                "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
                "Chat 1",
                "Some message...",
                UUID.randomUUID().toString()
            ),
            Chat(
                "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
                "Chat 2",
                "Some message...",
                UUID.randomUUID().toString()
            ),
            Chat(
                "https://cdn.pixabay.com/photo/2024/05/26/10/15/bird-8788491_1280.jpg",
                "Chat 3",
                "Some message...",
                UUID.randomUUID().toString()
            ),
            Chat(
                "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_640.jpg",
                "Chat 4",
                "Some message...",
                UUID.randomUUID().toString()
            )
        )

    private var chatMessages = chatList.associate {
        it.id to listOf(
            ChatMessage(
                Random.nextInt(),
                "new message1",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusDays(2)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message2",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusDays(1)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message3",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusHours(12)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message4",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusHours(9)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message5",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusHours(2)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message6",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusHours(2)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message7",
                ChatMessage.Type.OUTGOING,
                OffsetDateTime.now().minusHours(2)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message8",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusHours(1)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message9",
                ChatMessage.Type.OUTGOING,
                OffsetDateTime.now().minusHours(1)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message10",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusMinutes(1)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message11",
                ChatMessage.Type.INCOMING,
                OffsetDateTime.now().minusMinutes(1)
            ),
            ChatMessage(
                Random.nextInt(),
                "new message12",
                ChatMessage.Type.OUTGOING,
                OffsetDateTime.now().minusSeconds(25)
            )
        )
    }

    override fun getChats() = chatList

    override fun getMessages(id: String) = chatMessages[id]!!.sortedByDescending { it.dateTime }

    override fun sendMessage(chatId: String, message: String) {
        chatMessages = chatMessages.map {
            if (it.key == chatId) {
                it.key to it.value + ChatMessage(
                    Random.nextInt(),
                    message,
                    ChatMessage.Type.OUTGOING,
                    OffsetDateTime.now()
                )
            } else {
                it.key to it.value
            }
        }.toMap()
    }
}