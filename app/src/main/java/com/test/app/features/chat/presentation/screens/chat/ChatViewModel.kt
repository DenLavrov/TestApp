package com.test.app.features.chat.presentation.screens.chat

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.data.repository.IChatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

class ChatViewModel @AssistedInject constructor(
    private val chatRepository: IChatRepository,
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
            is ChatAction.Init -> flowOf(
                prevState.copy(
                    messages = chatRepository.getMessages(action.id),
                    id = action.id
                )
            )

            is ChatAction.UpdateText -> flowOf(prevState.copy(text = action.text))
            is ChatAction.Send -> action.text.takeIf { it.isNotEmpty() }?.let {
                flowOf(
                    chatRepository.sendMessage(
                        prevState.id,
                        it
                    )
                )
                    .map { prevState.copy(messages = chatRepository.getMessages(prevState.id)) }
                    .onCompletion { dispatch(ChatAction.UpdateText("")) }
            } ?: ignoreState()
        }
    }
}