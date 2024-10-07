package com.test.app.features.auth.presentation.di

import com.test.app.core.di.BaseComponentHolder
import com.test.app.core.di.CoreComponent

object AuthComponentHolder : BaseComponentHolder<AuthComponent, CoreComponent>() {

    override fun init(dependencies: CoreComponent?): AuthComponent {
        if (component == null)
            component = DaggerAuthComponent.builder().coreComponent(dependencies!!).build()
        return get()
    }
}