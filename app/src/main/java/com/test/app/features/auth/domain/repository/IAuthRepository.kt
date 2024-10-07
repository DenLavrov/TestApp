package com.test.app.features.auth.domain.repository

import com.test.app.features.auth.data.models.AuthResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    fun sendCode(phone: String): Flow<Boolean>

    fun login(phone: String, code: String): Flow<AuthResponse>

    fun register(phone: String, userName: String, name: String): Flow<AuthResponse>
}