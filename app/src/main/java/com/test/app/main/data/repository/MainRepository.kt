package com.test.app.main.data.repository

import com.test.app.core.data.Storage
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val storage: Storage
) : IMainRepository {
    override val isAuthorized = storage.keysFlow.filter {
        it == Storage.TOKEN_KEY
    }.map {
        storage.getString(it).isNullOrEmpty().not()
    }
}