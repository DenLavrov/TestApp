package com.test.app.features.auth.data.repository

import com.test.app.core.data.IRequester
import com.test.app.core.data.Storage
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
    private val storage: Storage,
    private val requester: IRequester
) : IAuthRepository, IRequester by requester {

    override fun sendCode(phone: String) = flow {
        makeRequest {
            val response = api.sendAuthCode(AuthCodeRequest(phone))
            emit(response.isSuccess)
        }
    }

    override fun login(phone: String, code: String) = flow {
        makeRequest {
            emit(api.checkAuthCode(CheckCodeRequest(phone, code)).save())
        }
    }

    override fun register(phone: String, userName: String, name: String) = flow {
        makeRequest {
            emit(api.register(RegisterRequest(phone, name, userName)).save())
        }
    }

    private suspend fun AuthResponse.save(): AuthResponse {
        token ?: return this
        refreshToken ?: return this
        storage.putString(Storage.REFRESH_TOKEN_KEY, refreshToken)
        storage.putString(Storage.TOKEN_KEY, token)
        return this
    }
}