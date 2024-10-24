package com.test.app.core.presentation.utils

import android.content.Context
import android.net.Uri
import com.test.app.core.data.Dispatchers
import kotlinx.coroutines.ensureActive
import java.util.Base64

@OptIn(ExperimentalUnsignedTypes::class)
suspend fun Uri.getBase64ImageData(context: Context, dispatchers: Dispatchers): String {
    val contentResolver = context.contentResolver ?: return ""
    return dispatchers.withIo {
        contentResolver.openInputStream(this@getBase64ImageData)?.use {
            val result = UByteArray(it.available())
            var index = 0
            while (it.available() > 0) {
                ensureActive()
                result[index++] = it.read().toUByte()
            }
            Base64.getEncoder().encodeToString(result.asByteArray())
        }.orEmpty()
    }
}