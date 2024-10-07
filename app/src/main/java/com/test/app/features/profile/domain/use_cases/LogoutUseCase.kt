package com.test.app.features.profile.domain.use_cases

import com.test.app.core.data.Storage
import com.test.app.features.profile.presentation.di.ProfileScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ProfileScope
class LogoutUseCase @Inject constructor(
    private val storage: Storage
) {
    operator fun invoke() = flow {
        storage.putString(Storage.PROFILE_KEY, null)
        storage.putString(Storage.REFRESH_TOKEN_KEY, null)
        storage.putString(Storage.TOKEN_KEY, null)
        emit(Unit)
    }
}