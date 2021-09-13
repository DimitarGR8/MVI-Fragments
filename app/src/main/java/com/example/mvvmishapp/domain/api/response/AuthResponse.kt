package com.example.mvvmishapp.domain.api.response

import kotlinx.serialization.SerialName

class AuthResponse(
    val message: String,
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("is_profile_created")
    val isProfileCreated: Boolean? = null
)