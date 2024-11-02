package com.test.app.features.chat.domain.use_cases

import com.test.app.core.Dispatchers
import com.test.app.features.chat.di.ChatScope
import com.test.app.features.chat.domain.repository.IChatRepository
import javax.inject.Inject

@ChatScope
class GetMessagesUseCase @Inject constructor(
    private val chatRepository: IChatRepository,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(chatId: String) = chatRepository.getMessages(chatId).let {
        dispatchers.withDefault {
            it.sortedByDescending { message -> message.dateTime }
        }
    }
}