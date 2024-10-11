package com.test.app.features.auth.data.repository

import com.test.app.core.data.Storage
import com.test.app.core.data.handleError
import com.test.app.features.auth.data.models.AuthCodeRequest
import com.test.app.features.auth.data.models.AuthResponse
import com.test.app.features.auth.data.models.CheckCodeRequest
import com.test.app.features.auth.data.models.RegisterRequest
import com.test.app.features.auth.data.network.AuthApi
import com.test.app.features.auth.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val storage: Storage
) : IAuthRepository {

    override fun sendCode(phone: String) = flow {
        val response = api.sendAuthCode(AuthCodeRequest(phone))
        emit(response.isSuccess)
    }.handleError()

    override fun login(phone: String, code: String) = flow {
        emit(api.checkAuthCode(CheckCodeRequest(phone, code)).save())
    }.handleError()

    override fun register(phone: String, userName: String, name: String) = flow {
        emit(api.register(RegisterRequest(phone, name, userName)).save())
    }.handleError()

    private suspend fun AuthResponse.save(): AuthResponse {
        storage.putString(Storage.REFRESH_TOKEN_KEY, refreshToken)
        storage.putString(Storage.TOKEN_KEY, token)
        return this
    }
}