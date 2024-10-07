package com.test.app.main.domain.repository

import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    val isAuthorized: Flow<Boolean>
}