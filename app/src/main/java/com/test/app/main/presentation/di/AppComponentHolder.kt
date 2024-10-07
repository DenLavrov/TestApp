package com.test.app.main.presentation.di

import com.test.app.core.di.BaseComponentHolder
import com.test.app.core.di.CoreComponent

object AppComponentHolder : BaseComponentHolder<AppComponent, CoreComponent>() {
    override fun init(dependencies: CoreComponent?): AppComponent {
        if (component == null)
            component = DaggerAppComponent.builder().coreComponent(dependencies!!).build()
        return get()
    }
}