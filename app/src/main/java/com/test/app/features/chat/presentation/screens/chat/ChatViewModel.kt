package com.test.app.features.chat.presentation.screens.chat

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.presentation.vm.BaseViewModel
import com.test.app.core.presentation.vm.ViewModelAssistedFactory
import com.test.app.features.chat.domain.use_cases.GetMessagesUseCase
import com.test.app.features.chat.domain.use_cases.SendMessageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion

class ChatViewModel @AssistedInject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatState, ChatAction>(
    ChatState.empty,
    ChatState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ChatViewModel>

    override suspend fun reduce(prevState: ChatState, action: ChatAction): Flow<ChatState> {
        return when (action) {
            is ChatAction.Init -> handleInitAction(action.id, prevState)
            is ChatAction.UpdateText -> flowOf(prevState.copy(text = action.text))
            is ChatAction.Send -> handleSendAction(action.text, prevState)
        }
    }

    private fun handleInitAction(
        chatId: String,
        prevState: ChatState
    ) = flowOf { getMessagesUseCase(chatId) }.toState {
        prevState.copy(
            messages = it,
            id = chatId
        )
    }

    private fun handleSendAction(
        message: String,
        prevState: ChatState
    ) = message.takeIf { it.isNotEmpty() }?.let {
        flowOf { sendMessageUseCase(prevState.id, message) }
            .toState { prevState.copy(messages = it) }
            .onCompletion { dispatch(ChatAction.UpdateText("")) }
    } ?: ignoreState()
}