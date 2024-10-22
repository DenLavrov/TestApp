package com.test.app.features.chat.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.domain.repository.IChatRepository
import com.test.app.features.chat.presentation.screens.chat.ChatViewModel
import com.test.app.features.chat.presentation.screens.chats.ChatsViewModel
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ChatModule::class])
@ChatScope
interface ChatComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun getProfileUseCase(getProfileUseCase: GetProfileUseCase): Builder
        fun build(): ChatComponent
    }

    fun chatRepository(): IChatRepository
    fun chatsVmFactory(): ViewModelAssistedFactory<ChatsViewModel>
    fun chatVmFactory(): ViewModelAssistedFactory<ChatViewModel>
}