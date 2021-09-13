package com.example.mvvmishapp.domain.dataSource.preferences

import android.content.SharedPreferences
import com.example.mvvmishapp.main.custom.extensions.currentThreadName
import com.example.mvvmishapp.main.custom.extensions.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPreferences(val sharedPreferences: SharedPreferences) {

    suspend fun saveToken(key: String, token: String) {
        withContext(Dispatchers.Default) {
            with(sharedPreferences.edit()) {
                key.log("Save User Token " + currentThreadName())
                putString(key, token)
                apply()
            }
        }
    }

    suspend fun getToken(key: String): String {
        return withContext(Dispatchers.Default) {
            key.log("Get User Token " + currentThreadName())
            sharedPreferences.getString(key, String()) ?: String()
        }
    }

    suspend fun clearToken(key: String) {
        return withContext(Dispatchers.Default) {
            with(sharedPreferences.edit()) {
                key.log("Clear User Token " + currentThreadName())
                remove(key)
                apply()
            }
        }
    }
}