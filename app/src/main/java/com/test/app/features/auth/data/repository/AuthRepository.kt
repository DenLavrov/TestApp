package com.test.app.features.auth.data.repository

import com.test.app.core.data.Dispatchers
import com.test.app.core.data.Storage
import com.test.app.core.data.runHttpRequest
import com.test.app.features.auth.data.models.AuthCodeRequest
import com.test.app.features.auth.data.models.AuthResponse
import com.test.app.features.auth.data.models.CheckCodeRequest
import com.test.app.features.auth.data.models.RegisterRequest
import com.test.app.features.auth.data.network.AuthApi
import com.test.app.features.auth.domain.repository.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val storage: Storage,
    private val dispatchers: Dispatchers
) : IAuthRepository {

    override suspend fun sendCode(phone: String) = runHttpRequest(dispatchers) {
        api.sendAuthCode(AuthCodeRequest(phone)).isSuccess
    }

    override suspend fun login(phone: String, code: String) = runHttpRequest(dispatchers) {
        api.checkAuthCode(CheckCodeRequest(phone, code)).save()
    }

    override suspend fun register(phone: String, userName: String, name: String) = runHttpRequest(dispatchers) {
        api.register(RegisterRequest(phone, name, userName)).save()
    }

    private suspend fun AuthResponse.save(): AuthResponse {
        storage.putString(Storage.REFRESH_TOKEN_KEY, refreshToken)
        storage.putString(Storage.TOKEN_KEY, token)
        return this
    }
}