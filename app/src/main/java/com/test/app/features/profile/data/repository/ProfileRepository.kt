package com.test.app.features.profile.data.repository

import com.test.app.core.data.Dispatchers
import com.test.app.core.data.Storage
import com.test.app.core.data.runHttpRequest
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.data.models.ProfileData
import com.test.app.features.profile.data.models.UpdateProfileRequest
import com.test.app.features.profile.data.network.ProfileApi
import com.test.app.features.profile.domain.repository.IProfileRepository
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val storage: Storage,
    private val dispatchers: Dispatchers
) : IProfileRepository {

    override suspend fun getProfile(force: Boolean) = runHttpRequest(dispatchers) {
        if (force) {
            return@runHttpRequest api.getProfile().data.save()
        }
        val profile = storage.getSerializable(Storage.PROFILE_KEY) ?: api.getProfile().data
        profile.save()
    }

    override suspend fun updateProfile(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData?,
        birthday: String?,
        about: String?,
        city: String?
    ) = runHttpRequest(dispatchers) {
        api.updateProfile(
            UpdateProfileRequest(
                username,
                phone,
                name,
                avatar,
                birthday,
                city,
                about
            )
        )
    }

    private suspend fun ProfileData.save(): ProfileData {
        storage.putSerializable(Storage.PROFILE_KEY, this)
        return this
    }
}