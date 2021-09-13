package com.example.mvvmishapp.main.custom.managers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mvvmishapp.R
import com.example.mvvmishapp.databinding.LayoutSnackbarBinding
import com.example.mvvmishapp.main.custom.extensions.onClick
import com.google.android.material.snackbar.Snackbar

object DialogsManager {

    fun showToast(context: Context, @StringRes messageRes: Int) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("InflateParams")
    fun showSnackBar(activity: Activity, option: DialogOptions) {
        val contentView: View = activity.findViewById(android.R.id.content)
        val snackBar = Snackbar.make(contentView, "", Snackbar.LENGTH_LONG)
        val layout = snackBar.view as Snackbar.SnackbarLayout
        val customLayout = LayoutSnackbarBinding.inflate(activity.layoutInflater)

        with(layout) {
            getChildAt(0).visibility = View.INVISIBLE
            setBackgroundResource(android.R.color.transparent)
            setPadding(0, 0, 0, 0)
            addView(customLayout.root, 0)
        }

        with(customLayout) {
            tvTitle.text = option.title ?: activity.getString(option.titleRes ?: 0)
            tvMessage.text = option.message ?: activity.getString(option.messageRes ?: 0)
            ivIcon.setImageResource(option.icon)
            ivClose.onClick {
                snackBar.dismiss()
            }
        }

        snackBar.show()
    }
}

sealed class DialogOptions(
    val title: String?,
    @StringRes open val titleRes: Int?,
    val message: String?,
    @StringRes val messageRes: Int?,
    @DrawableRes val icon: Int
) {
    class GeneralError(error: String? = null) : DialogOptions(
        title = null,
        titleRes = R.string.warning_general_title,
        messageRes = R.string.warning_general_message,
        message = error,
        icon = R.drawable.ic_play
    )

    class CustomError(error: String, title: String) : DialogOptions(
        title = title,
        titleRes = null,
        messageRes = null,
        message = error,
        icon = R.drawable.ic_play
    )
}