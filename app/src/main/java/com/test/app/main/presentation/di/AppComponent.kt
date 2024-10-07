package com.test.app.main.presentation.di

import com.test.app.core.di.CoreComponent
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.main.presentation.MainViewModel
import dagger.Component

@Component(modules = [AppModule::class], dependencies = [CoreComponent::class])
@AppScope
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): AppComponent
    }

    fun mainVmFactory(): ViewModelAssistedFactory<MainViewModel>
}