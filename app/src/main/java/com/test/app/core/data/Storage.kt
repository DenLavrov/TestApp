package com.test.app.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.test.app.core.di.CoreScope
import com.test.app.core.di.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import javax.inject.Inject

@CoreScope
class Storage @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object Keys {
        const val TOKEN_KEY = "TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        const val PROFILE_KEY = "PROFILE_KEY"
    }

    private val _keysFlow = MutableSharedFlow<String>()
    val keysFlow: Flow<String> = _keysFlow

    fun getString(key: String) = sharedPreferences.getString(key, null)

    suspend fun putString(key: String, value: String?) {
        sharedPreferences.edit {
            putString(key, value)
        }
        _keysFlow.emit(key)
    }

    inline fun <reified T> getSerializable(key: String): T? {
        return getString(key)?.let {
            json.decodeFromString(it)
        }
    }

    suspend inline fun <reified T> putSerializable(key: String, value: T) {
        putString(key, json.encodeToString(value))
    }
}