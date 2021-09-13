package com.example.mvvmishapp.domain.repository

import com.example.mvvmishapp.domain.dataSource.preferences.UserPreferences
import com.example.mvvmishapp.domain.model.enums.UserDataKeys

class CredentialsRepository(private val userPreferences: UserPreferences) {
    suspend fun saveDeviceID(token: String) {
        userPreferences.saveToken(UserDataKeys.DEVICE_TOKEN.name, token)
    }

    suspend fun getDeviceId(): String {
        return userPreferences.getToken(UserDataKeys.DEVICE_TOKEN.name)
    }

    suspend fun savePushToken(token: String) {
        userPreferences.saveToken(UserDataKeys.PUSH_TOKEN.name, token)
    }

    suspend fun getPushToken(): String {
        return userPreferences.getToken(UserDataKeys.PUSH_TOKEN.name)
    }

    suspend fun saveAuthToken(token: String) {
        userPreferences.saveToken(UserDataKeys.AUTH_TOKEN.name, token)
    }

    suspend fun getAuthToken(): String {
        return userPreferences.getToken(UserDataKeys.AUTH_TOKEN.name)
    }

    fun getToken(): String = userPreferences.sharedPreferences
        .getString(UserDataKeys.AUTH_TOKEN.name, String()) ?: String()

    suspend fun clear() {
        userPreferences.clearToken(UserDataKeys.AUTH_TOKEN.name)
    }
}