package com.example.mvvmishapp.main.custom.extensions

import android.util.Log

fun Any?.log(key: String) {
    Log.e(key, toString())
}