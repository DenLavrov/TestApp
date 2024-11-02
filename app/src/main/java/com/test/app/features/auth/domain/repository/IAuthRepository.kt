package com.test.app.features.auth.domain.repository

interface IAuthRepository {

    suspend fun sendCode(phone: String): Boolean

    suspend fun login(phone: String, code: String): Boolean

    suspend fun register(phone: String, userName: String, name: String)
}