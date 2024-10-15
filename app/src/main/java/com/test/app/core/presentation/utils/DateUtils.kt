package com.test.app.core.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertMillisToYyyyMMddFormat(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}