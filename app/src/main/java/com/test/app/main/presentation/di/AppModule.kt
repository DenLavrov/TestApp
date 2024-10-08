package com.test.app.main.presentation.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.main.data.repository.MainRepository
import com.test.app.main.data.repository.IMainRepository
import com.test.app.main.presentation.MainViewModel
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule.Bindings::class])
class AppModule {

    @Module
    interface Bindings {
        @Binds
        @AppScope
        fun bindMainVmFactory(factory: MainViewModel.Factory): ViewModelAssistedFactory<MainViewModel>

        @Binds
        @AppScope
        fun bindMainRepository(repository: MainRepository): IMainRepository
    }
}