package com.test.app.main.data.repository

import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    val isAuthorized: Flow<Boolean>
}