package com.test.app.features.profile.domain.use_cases

import com.test.app.features.profile.di.ProfileScope
import com.test.app.features.profile.domain.models.AvatarData
import com.test.app.features.profile.domain.models.Profile
import com.test.app.features.profile.domain.repository.IProfileRepository
import javax.inject.Inject

@ProfileScope
class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: IProfileRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData? = null,
        birthday: String? = null,
        about: String? = null,
        city: String? = null
    ): Profile {
        profileRepository.updateProfile(username, phone, name, avatar, birthday, about, city)
        return getProfileUseCase(true)
    }
}