package com.test.app.features.chat.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.features.chat.di.ChatScope
import com.test.app.features.chat.domain.repository.IChatRepository
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ChatScope
class GetMessagesUseCase @Inject constructor(
    private val chatRepository: IChatRepository,
    private val dispatchers: Dispatchers
) {
    operator fun invoke(chatId: String) = chatRepository.getMessages(chatId)
        .map { it.sortedByDescending { message -> message.dateTime } }
        .flowOn(dispatchers.default)
}