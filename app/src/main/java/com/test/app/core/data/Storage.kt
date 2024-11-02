package com.test.app.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.test.app.core.Dispatchers
import com.test.app.core.di.CoreScope
import com.test.app.core.json
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.encodeToString
import javax.inject.Inject

@CoreScope
class Storage @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    val dispatchers: Dispatchers
) {

    companion object Keys {
        const val TOKEN_KEY = "TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        const val PROFILE_KEY = "PROFILE_KEY"
    }

    private val _keysFlow = MutableSharedFlow<String>()
    val keysFlow = _keysFlow.asSharedFlow()

    fun getString(key: String) = sharedPreferences.getString(key, null)

    suspend fun putString(key: String, value: String?) = dispatchers.withDefault {
        sharedPreferences.edit {
            putString(key, value)
        }
        _keysFlow.emit(key)
    }

    suspend inline fun <reified T> getSerializable(key: String): T? {
        return dispatchers.withDefault {
            getString(key)?.let {
                json.decodeFromString(it)
            }
        }
    }

    suspend inline fun <reified T> putSerializable(key: String, value: T) =
        dispatchers.withDefault {
            putString(key, json.encodeToString(value))
        }
}