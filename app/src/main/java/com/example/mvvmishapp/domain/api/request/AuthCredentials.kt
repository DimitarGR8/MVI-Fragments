package com.example.mvvmishapp.domain.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthCredentials(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String)