package com.example.mvvmishapp.main.custom.extensions

import java.util.*

fun generateRandomString(): String = UUID.randomUUID().toString()

fun currentThreadName(): String = "current thread - ${Thread.currentThread().name}"

fun Boolean.toInt() = if (this) 1 else 0