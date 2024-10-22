package com.test.app.features.profile.di

import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.profile.data.network.ProfileApi
import com.test.app.features.profile.data.repository.ProfileRepository
import com.test.app.features.profile.domain.repository.IProfileRepository
import com.test.app.features.profile.presentation.screens.edit_profile.EditProfileViewModel
import com.test.app.features.profile.presentation.screens.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [ProfileModule.Bindings::class])
class ProfileModule {

    @Provides
    @ProfileScope
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Module
    interface Bindings {

        @Binds
        @ProfileScope
        fun bindProfileVmFactory(factory: ProfileViewModel.Factory): ViewModelAssistedFactory<ProfileViewModel>

        @Binds
        @ProfileScope
        fun bindProfileRepository(profileRepository: ProfileRepository): IProfileRepository

        @Binds
        @ProfileScope
        fun bindEditProfileVmFactory(factory: EditProfileViewModel.Factory): ViewModelAssistedFactory<EditProfileViewModel>
    }
}