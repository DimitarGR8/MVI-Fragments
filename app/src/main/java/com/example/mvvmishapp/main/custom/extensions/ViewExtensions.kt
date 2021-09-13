package com.example.mvvmishapp.main.custom.extensions

import android.view.View

fun View.onClick(listener: (View) -> Unit) {
    tag = true
    setOnClickListener {
        if (tag as Boolean) {
            listener.invoke(it)
            postDelayed({
                tag = true
            }, 500)
        }
        tag = false
    }
}
