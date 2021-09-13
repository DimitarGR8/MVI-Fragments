package com.example.mvvmishapp.main.custom.extensions

import android.util.DisplayMetrics
import androidx.fragment.app.Fragment

fun Fragment.getDisplay() =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        activity?.display
    } else {
        @Suppress("DEPRECATION")
        activity?.windowManager?.defaultDisplay
    }

fun Fragment.getWidthPercent(percent: Float): Int {
    val displayMetrics = DisplayMetrics()
    getDisplay()?.getRealMetrics(displayMetrics)
    return (displayMetrics.widthPixels * percent).toInt()
}

fun Fragment.getHeightPercent(percent: Float): Int {
    val displayMetrics = DisplayMetrics()
    getDisplay()?.getRealMetrics(displayMetrics)
    return (displayMetrics.heightPixels * percent).toInt()
}
