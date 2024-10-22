package com.test.app.features.profile.di

import com.test.app.core.di.BaseComponentHolder
import com.test.app.core.di.CoreComponent

object ProfileComponentHolder : BaseComponentHolder<ProfileComponent, CoreComponent>() {
    override fun init(dependencies: CoreComponent?): ProfileComponent {
        if (component == null)
            component = DaggerProfileComponent.builder().coreComponent(dependencies!!).build()
        return get()
    }
}