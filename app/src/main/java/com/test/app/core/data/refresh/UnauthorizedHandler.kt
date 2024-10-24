package com.test.app.core.data.refresh

import com.test.app.core.data.Storage
import com.test.app.core.data.refresh.models.RefreshTokenRequest
import com.test.app.core.data.refresh.models.RefreshTokenResponse
import dagger.Lazy
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UnauthorizedHandler @Inject constructor(
    private val refreshApi: Lazy<RefreshApi>,
    private val storage: Storage
) : Authenticator {
    private val mutex by lazy { Mutex() }
    private var currentJob = lazy { Job() }

    override fun authenticate(route: Route?, response: Response): Request {
        try {
            runBlocking(currentJob.value) {
                mutex.withLock(currentJob.value) {
                    try {
                        val refreshToken = storage.getString(Storage.REFRESH_TOKEN_KEY)!!
                        refreshApi.get().refreshToken(RefreshTokenRequest(refreshToken)).save()
                    } catch (t: Throwable) {
                        RefreshTokenResponse.empty.save()
                        ensureActive()
                    } finally {
                        currentJob.value.cancel()
                    }
                }
            }
        } catch (_: CancellationException) {
        } finally {
            if (mutex.holdsLock(currentJob.value).not())
                currentJob = lazy { Job() }
        }
        return response.request
    }

    private suspend fun RefreshTokenResponse.save(): RefreshTokenResponse {
        storage.putString(Storage.REFRESH_TOKEN_KEY, refreshToken)
        storage.putString(Storage.TOKEN_KEY, token)
        return this
    }
}