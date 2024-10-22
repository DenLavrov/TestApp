package com.test.app.features.profile.data.repository

import com.test.app.core.data.Dispatchers
import com.test.app.core.data.Storage
import com.test.app.core.data.handleError
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.data.models.ProfileData
import com.test.app.features.profile.data.models.UpdateProfileRequest
import com.test.app.features.profile.data.network.ProfileApi
import com.test.app.features.profile.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val storage: Storage,
    private val dispatchers: Dispatchers
) : IProfileRepository {

    override fun getProfile(force: Boolean) = flow {
        if (force) {
            emit(api.getProfile().data.save())
            return@flow
        }
        val profile = storage.getSerializable(Storage.PROFILE_KEY) ?: api.getProfile().data
        emit(profile.save())
    }.handleError().flowOn(dispatchers.io)

    override fun updateProfile(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData?,
        birthday: String?,
        about: String?,
        city: String?
    ) = flow {
        emit(
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
        )
    }.handleError().flowOn(dispatchers.io)

    private suspend fun ProfileData.save(): ProfileData {
        storage.putSerializable(Storage.PROFILE_KEY, this)
        return this
    }
}