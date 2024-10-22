package com.test.app.features.chat.presentation.screens.chat

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.domain.repository.IChatRepository
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

    override fun reduce(prevState: ChatState, action: ChatAction): Flow<ChatState> {
        return when (action) {
            is ChatAction.Init -> getMessagesUseCase(action.id).toState {
                prevState.copy(
                    messages = it,
                    id = action.id
                )
            }

            is ChatAction.UpdateText -> flowOf(prevState.copy(text = action.text))
            is ChatAction.Send -> action.text.takeIf { it.isNotEmpty() }?.let {
                sendMessageUseCase(prevState.id, action.text)
                    .toState { prevState.copy(messages = it) }
                    .onCompletion { dispatch(ChatAction.UpdateText("")) }
            } ?: ignoreState()
        }
    }
}