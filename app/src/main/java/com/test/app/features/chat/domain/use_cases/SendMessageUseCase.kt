package com.test.app.features.chat.domain.use_cases

import com.test.app.features.chat.data.models.ChatMessage
import com.test.app.features.chat.domain.repository.IChatRepository
import com.test.app.features.chat.di.ChatScope
import javax.inject.Inject

@ChatScope
class SendMessageUseCase @Inject constructor(
    private val chatRepository: IChatRepository,
    private val getMessagesUseCase: GetMessagesUseCase
) {
    suspend operator fun invoke(chatId: String, message: String): List<ChatMessage> {
        chatRepository.sendMessage(chatId, message)
        return getMessagesUseCase(chatId)
    }
}