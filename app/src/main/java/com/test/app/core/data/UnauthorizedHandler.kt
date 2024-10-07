package com.test.app.core.data

import com.test.app.core.data.models.RefreshTokenRequest
import com.test.app.core.data.models.RefreshTokenResponse
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class UnauthorizedHandler @Inject constructor(
    private val refreshApi: Lazy<RefreshApi>,
    private val storage: Storage
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        runBlocking {
            val refreshToken = storage.getString(Storage.REFRESH_TOKEN_KEY) ?: return@runBlocking
            try {
                refreshApi.get().refreshToken(RefreshTokenRequest(refreshToken)).save()
            } catch (t: Throwable) {
                RefreshTokenResponse.empty.save()
            }
        }
        return response.request
    }

    private suspend fun RefreshTokenResponse.save(): RefreshTokenResponse {
        storage.putString(Storage.REFRESH_TOKEN_KEY, refreshToken)
        storage.putString(Storage.TOKEN_KEY, token)
        return this
    }
}