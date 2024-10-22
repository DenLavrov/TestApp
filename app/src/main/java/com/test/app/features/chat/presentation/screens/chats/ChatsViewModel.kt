package com.test.app.features.chat.presentation.screens.chats

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.domain.repository.IChatRepository
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class ChatsViewModel @AssistedInject constructor(
    private val chatRepository: IChatRepository,
    private val getProfileUseCase: GetProfileUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatsState, ChatsAction>(
    ChatsState.empty,
    ChatsState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ChatsViewModel>

    override suspend fun reduce(prevState: ChatsState, action: ChatsAction): Flow<ChatsState> {
        return when (action) {
            ChatsAction.Init -> flowOf { getProfileUseCase() }
                .toState(
                    onContent = {
                        prevState.copy(
                            data = chatRepository.getChats(),
                            avatar = it.avatar?.filename
                        )
                    },
                    onError = {
                        prevState.copy(data = chatRepository.getChats())
                    }
                )
        }
    }
}