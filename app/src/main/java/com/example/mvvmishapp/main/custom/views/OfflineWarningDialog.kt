package com.example.mvvmishapp.main.custom.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.mvvmishapp.databinding.LayoutSnackbarBinding

class OfflineWarningDialog(context: Context) : AlertDialog(context) {

    lateinit var dialogView: LayoutSnackbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        dialogView = LayoutSnackbarBinding.inflate(layoutInflater)
        this.setView(dialogView.root)
        this.setCancelable(false)
        super.onCreate(savedInstanceState)
    }

    fun showDialog(): OfflineWarningDialog {
        this.show()
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.BOTTOM)

        dialogView.tvTitle.text = "You are offline"
        dialogView.tvMessage.text = "Please check your internet connection"

       // dialogView.ivIcon.setImageResource(Set offline image here)
        dialogView.ivClose.isVisible = false
        return this
    }

}