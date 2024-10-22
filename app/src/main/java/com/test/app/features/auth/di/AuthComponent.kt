package com.test.app.features.auth.di

import com.test.app.core.di.CoreComponent
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.auth.presentation.screens.code.CodeViewModel
import com.test.app.features.auth.presentation.screens.phone.PhoneViewModel
import com.test.app.features.auth.presentation.screens.register.RegisterViewModel
import dagger.Component

@Component(modules = [AuthModule::class], dependencies = [CoreComponent::class])
@AuthScope
interface AuthComponent {

    @Component.Builder
    interface Builder {
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): AuthComponent
    }

    fun phoneVmFactory(): ViewModelAssistedFactory<PhoneViewModel>

    fun codeVmFactory(): ViewModelAssistedFactory<CodeViewModel>

    fun registerVmFactory(): ViewModelAssistedFactory<RegisterViewModel>
}