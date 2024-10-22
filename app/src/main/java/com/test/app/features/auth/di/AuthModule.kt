package com.test.app.features.auth.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.auth.data.network.AuthApi
import com.test.app.features.auth.data.repository.AuthRepository
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.presentation.screens.code.CodeViewModel
import com.test.app.features.auth.presentation.screens.phone.PhoneViewModel
import com.test.app.features.auth.presentation.screens.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [AuthModule.Bindings::class])
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Module
    interface Bindings {

        @AuthScope
        @Binds
        fun bindAuthRepository(repository: AuthRepository): IAuthRepository

        @AuthScope
        @Binds
        fun bindPhoneVmFactory(factory: PhoneViewModel.Factory): ViewModelAssistedFactory<PhoneViewModel>

        @AuthScope
        @Binds
        fun bindCodeVmFactory(factory: CodeViewModel.Factory): ViewModelAssistedFactory<CodeViewModel>

        @AuthScope
        @Binds
        fun bindRegisterVmFactory(factory: RegisterViewModel.Factory): ViewModelAssistedFactory<RegisterViewModel>
    }
}