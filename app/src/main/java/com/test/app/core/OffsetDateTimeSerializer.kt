package com.test.app.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime

object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val str = decoder.decodeString()
        return OffsetDateTime.parse(str)
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        return encoder.encodeString(value.toLocalDateTime().toString())
    }
}