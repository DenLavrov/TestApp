package com.test.app.features.profile.data.repository

import com.test.app.core.Dispatchers
import com.test.app.core.data.Storage
import com.test.app.core.data.runHttpRequest
import com.test.app.features.profile.data.models.UpdateProfileRequest
import com.test.app.features.profile.data.network.ProfileApi
import com.test.app.features.profile.domain.models.AvatarData
import com.test.app.features.profile.domain.models.response.ProfileData
import com.test.app.features.profile.domain.repository.IProfileRepository
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val storage: Storage,
    private val dispatchers: Dispatchers
) : IProfileRepository {

    override suspend fun getProfile(force: Boolean) = runHttpRequest {
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
    ) = runHttpRequest {
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

    override suspend fun logout() {
        dispatchers.withDefault {
            storage.putString(Storage.PROFILE_KEY, null)
            storage.putString(Storage.REFRESH_TOKEN_KEY, null)
            storage.putString(Storage.TOKEN_KEY, null)
        }
    }

    private suspend fun ProfileData.save(): ProfileData {
        storage.putSerializable(Storage.PROFILE_KEY, this)
        return this
    }
}