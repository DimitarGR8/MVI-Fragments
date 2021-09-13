package com.example.mvvmishapp.domain.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("firstname")
    val firstname: String? = null
)