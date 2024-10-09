package com.test.app.features.chat.presentation.di

import com.test.app.core.di.BaseComponentHolder
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase

object ChatComponentHolder : BaseComponentHolder<ChatComponent, GetProfileUseCase>() {
    override fun init(dependencies: GetProfileUseCase?): ChatComponent {
        if (component == null)
            component = DaggerChatComponent.builder().getProfileUseCase(dependencies!!).build()
        return get()
    }
}