package com.test.app.features.profile.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.test.app.core.data.IRequester
import com.test.app.core.data.Storage
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.data.models.ProfileData
import com.test.app.features.profile.data.models.UpdateProfileRequest
import com.test.app.features.profile.data.network.ProfileApi
import com.test.app.features.profile.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val storage: Storage,
    private val requester: IRequester
) : IProfileRepository, IRequester by requester {

    override fun getProfile(force: Boolean) = flow {
        if (force) {
            makeRequest { emit(api.getProfile().data.save()) }
            return@flow
        }
        var profile: ProfileData? = storage.getSerializable(Storage.PROFILE_KEY)
        if (profile == null)
            makeRequest {
                profile = api.getProfile().data
            }
        emit(profile!!.save())
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun updateProfile(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData?,
        birthday: String?,
        about: String?,
        city: String?
    ) = flow {
        makeRequest {
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
        }
    }

    private suspend fun ProfileData.save(): ProfileData {
        storage.putSerializable(Storage.PROFILE_KEY, this)
        return this
    }
}