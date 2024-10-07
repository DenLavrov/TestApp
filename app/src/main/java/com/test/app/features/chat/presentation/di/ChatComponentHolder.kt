package com.test.app.features.chat.presentation.di

import com.test.app.core.di.BaseComponentHolder
import com.test.app.core.di.CoreComponent
import com.test.app.features.profile.presentation.di.ProfileComponent

object ChatComponentHolder : BaseComponentHolder<ChatComponent, ProfileComponent>() {
    override fun init(dependencies: ProfileComponent?): ChatComponent {
        if (component == null)
            component = DaggerChatComponent.builder().profileComponent(dependencies!!).build()
        return get()
    }
}