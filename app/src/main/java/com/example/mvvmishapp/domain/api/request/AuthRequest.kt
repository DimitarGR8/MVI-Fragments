package com.example.mvvmishapp.domain.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthRequest(
    @SerialName("user_credentials")
    val credentials: AuthCredentials,

    @SerialName("device_token")
    val deviceToken: String,

    @SerialName("device_type")
    val deviceType: String,

    @SerialName("push_token")
    val pushToken: String
)