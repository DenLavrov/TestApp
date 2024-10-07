package com.test.app.features.profile.presentation.di

import com.test.app.core.di.CoreComponent
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.profile.domain.repository.IProfileRepository
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase
import com.test.app.features.profile.presentation.screens.edit_profile.EditProfileViewModel
import com.test.app.features.profile.presentation.screens.profile.ProfileViewModel
import dagger.Component

@Component(modules = [ProfileModule::class], dependencies = [CoreComponent::class])
@ProfileScope
interface ProfileComponent {

    @Component.Builder
    interface Builder {
        fun coreComponent(component: CoreComponent): Builder
        fun build(): ProfileComponent
    }

    fun profileRepository(): IProfileRepository

    fun getProfileUseCase(): GetProfileUseCase

    fun profileVmFactory(): ViewModelAssistedFactory<ProfileViewModel>

    fun editProfileVmFactory(): ViewModelAssistedFactory<EditProfileViewModel>
}