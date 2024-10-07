package com.test.app.features.chat.presentation.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.chat.data.repository.IChatRepository
import com.test.app.features.chat.presentation.screens.chat.ChatViewModel
import com.test.app.features.chat.presentation.screens.chats.ChatsViewModel
import com.test.app.features.profile.presentation.di.ProfileComponent
import dagger.Component

@Component(modules = [ChatModule::class], dependencies = [ProfileComponent::class])
@ChatScope
interface ChatComponent {
    @Component.Builder
    interface Builder {
        fun profileComponent(profileComponent: ProfileComponent): Builder
        fun build(): ChatComponent
    }

    fun chatRepository(): IChatRepository
    fun chatsVmFactory(): ViewModelAssistedFactory<ChatsViewModel>
    fun chatVmFactory(): ViewModelAssistedFactory<ChatViewModel>
}