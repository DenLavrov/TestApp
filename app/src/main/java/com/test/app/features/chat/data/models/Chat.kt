package com.test.app.features.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Chat(val image: String, val title: String, val message: String, val id: String)
