package com.test.app.core.presentation.utils

import android.content.Context
import android.net.Uri
import java.util.Base64

fun Uri.getBase64ImageData(context: Context): String {
    val contentResolver = context.contentResolver ?: return ""
    return contentResolver.openInputStream(this)?.use {
        Base64.getEncoder().encodeToString(it.readBytes())
    }.orEmpty()
}