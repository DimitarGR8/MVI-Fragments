package com.example.mvvmishapp.main.custom.extensions

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import com.example.mvvmishapp.R

fun Window.setStatusBarBackground(isLightBackground: Boolean) {
    val backgroundRes = if (isLightBackground)
        R.color.white
    else
        R.color.black
    setBackgroundDrawableResource(backgroundRes)
    changeStatusBarColor(isLightBackground)
}

@Suppress("DEPRECATION")
fun Window.drawUnderStatusBar() {
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

@Suppress("DEPRECATION")
private fun Window.changeStatusBarColor(isLightBackground: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        var flags = decorView.systemUiVisibility

        flags = if (isLightBackground)
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        decorView.systemUiVisibility = flags
    } else {
        val flag = if (isLightBackground)
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        else
            0
        insetsController?.setSystemBarsAppearance(
            flag,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}