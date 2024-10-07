package com.test.app.features.chat.data.models

import com.test.app.core.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class ChatMessage(
    val id: Int,
    val message: String,
    val type: Type,
    @Serializable(with = OffsetDateTimeSerializer::class) val dateTime: OffsetDateTime
) {

    @Serializable
    enum class Type {
        OUTGOING,
        INCOMING
    }
}