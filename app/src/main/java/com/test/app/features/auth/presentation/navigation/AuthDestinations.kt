package com.test.app.features.auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Phone

@Serializable
data class Code(val phone: String)

@Serializable
data class Register(val phone: String)