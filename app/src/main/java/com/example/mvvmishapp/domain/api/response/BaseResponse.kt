package com.example.mvvmishapp.domain.api.response

import kotlinx.serialization.Serializable

@Serializable
class BaseResponse<T>(
    val data: T
)