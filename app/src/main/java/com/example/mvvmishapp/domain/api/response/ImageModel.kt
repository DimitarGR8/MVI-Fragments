package com.example.mvvmishapp.domain.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageModel(
    val id: Int,
    val url: String,
    val length: Long? = null
) : java.io.Serializable