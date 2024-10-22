package com.test.app.features.profile.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.di.ProfileScope
import com.test.app.features.profile.domain.repository.IProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ProfileScope
class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: IProfileRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData? = null,
        birthday: String? = null,
        about: String? = null,
        city: String? = null
    ) = profileRepository.updateProfile(username, phone, name, avatar, birthday, about, city)
        .flatMapConcat { getProfileUseCase(true) }
}