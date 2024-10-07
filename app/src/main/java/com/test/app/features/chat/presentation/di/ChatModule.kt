package com.test.app.features.chat.presentation.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.data.repository.ChatRepository
import com.test.app.features.chat.data.repository.IChatRepository
import com.test.app.features.chat.presentation.screens.chat.ChatViewModel
import com.test.app.features.chat.presentation.screens.chats.ChatsViewModel
import dagger.Binds
import dagger.Module

@Module(includes = [ChatModule.Bindings::class])
class ChatModule {

    @Module
    interface Bindings {
        @Binds
        @ChatScope
        fun bindChatRepository(chatRepository: ChatRepository): IChatRepository

        @Binds
        @ChatScope
        fun bindChatsVmFactory(viewModel: ChatsViewModel.Factory): ViewModelAssistedFactory<ChatsViewModel>

        @Binds
        @ChatScope
        fun bindChatVmFactory(viewModel: ChatViewModel.Factory): ViewModelAssistedFactory<ChatViewModel>
    }
}