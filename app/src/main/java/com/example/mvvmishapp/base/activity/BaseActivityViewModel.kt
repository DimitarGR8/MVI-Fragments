package com.example.mvvmishapp.base.activity

import androidx.lifecycle.ViewModel
import com.example.mvvmishapp.base.activity.handlers.CommandHandler
import com.example.mvvmishapp.base.activity.handlers.MainHandler
import kotlinx.coroutines.FlowPreview
import java.lang.ref.WeakReference

@FlowPreview
abstract class BaseActivityViewModel : ViewModel(), CommandHandler {

    private var mainHandler: WeakReference<MainHandler>? = null

    fun bindView(mainHandler: MainHandler) {
        this.mainHandler = WeakReference(mainHandler)
        onBind()
    }

    abstract fun onBind()

    fun unbindView() {
        mainHandler = null
    }

    protected fun requireHandler(callback: (mainHandler: MainHandler) -> Unit) {
        mainHandler?.get()?.run {
            callback.invoke(this)
        }
    }
}