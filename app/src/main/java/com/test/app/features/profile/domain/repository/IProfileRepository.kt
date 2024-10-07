package com.test.app.features.profile.domain.repository

import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.data.models.ProfileData
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {
    fun getProfile(force: Boolean = false): Flow<ProfileData>

    fun updateProfile(
        username: String,
        phone: String,
        name: String,
        avatar: AvatarData? = null,
        birthday: String? = null,
        about: String? = null,
        city: String? = null
    ): Flow<Unit>
}