package com.test.app.features.chat.domain.use_cases

import com.test.app.features.chat.domain.repository.IChatRepository
import com.test.app.features.chat.di.ChatScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@ChatScope
class SendMessageUseCase @Inject constructor(
    private val chatRepository: IChatRepository,
    private val getMessagesUseCase: GetMessagesUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(chatId: String, message: String) =
        chatRepository.sendMessage(chatId, message)
            .flatMapConcat { getMessagesUseCase(chatId) }
}