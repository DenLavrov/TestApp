package com.test.app.core

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

object Constants {
    const val BASE_URL = "https://plannerok.ru"
}