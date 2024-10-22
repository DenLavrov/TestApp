package com.test.app.features.profile.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.core.data.Storage
import com.test.app.features.profile.di.ProfileScope
import javax.inject.Inject

@ProfileScope
class LogoutUseCase @Inject constructor(
    private val storage: Storage,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke() = dispatchers.withDefault {
        storage.putString(Storage.PROFILE_KEY, null)
        storage.putString(Storage.REFRESH_TOKEN_KEY, null)
        storage.putString(Storage.TOKEN_KEY, null)
    }
}