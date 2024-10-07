package com.test.app.features.chat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class Chat(val id: String, val title: String, val image: String)

@Serializable
data object Chats