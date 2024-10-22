package com.test.app.features.auth.domain.repository

import com.test.app.features.auth.data.models.AuthResponse

interface IAuthRepository {

    suspend fun sendCode(phone: String): Boolean

    suspend fun login(phone: String, code: String): AuthResponse

    suspend fun register(phone: String, userName: String, name: String): AuthResponse
}