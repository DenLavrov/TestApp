package com.test.app.features.profile.domain.use_cases

import com.test.app.features.profile.di.ProfileScope
import com.test.app.features.profile.domain.models.toDomain
import com.test.app.features.profile.domain.repository.IProfileRepository
import javax.inject.Inject

@ProfileScope
class GetProfileUseCase @Inject constructor(
    private val profileRepository: IProfileRepository
) {
    suspend operator fun invoke(force: Boolean = false) =
        profileRepository.getProfile(force).toDomain()
}