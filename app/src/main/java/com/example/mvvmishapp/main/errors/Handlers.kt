package com.example.mvvmishapp.main.errors

import com.example.mvvmishapp.main.custom.managers.DialogOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Response

fun Exception.getMessage(): DialogOptions {
    return when (this) {
        is ApiException -> DialogOptions.CustomError(title = "api", error = message!!)
        else -> DialogOptions.GeneralError(message)
    }
}

fun Response<*>.getErrorMessage(): String = "${code()} ${message()}"