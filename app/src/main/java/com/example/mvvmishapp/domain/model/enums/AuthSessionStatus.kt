package com.example.mvvmishapp.domain.model.enums

sealed class AuthSessionStatus {
    object Unauthorized : AuthSessionStatus()
    object Authorized: AuthSessionStatus()
}