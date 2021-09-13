package com.example.mvvmishapp.base.activity.handlers

import com.example.mvvmishapp.base.activity.commands.FragmentActionCommand
import com.example.mvvmishapp.base.activity.commands.FragmentActionCommandResult
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationOptions
import com.example.mvvmishapp.domain.model.enums.AuthSessionStatus
import com.example.mvvmishapp.main.custom.managers.DialogOptions
import kotlinx.coroutines.FlowPreview

@FlowPreview
interface MainHandler {
    fun initAppSession(status: AuthSessionStatus)
    fun popBackStack(upTo: NavigationOptions?)
    fun executeNavigation(item: NavigationOptions)
    fun showWarning(message: DialogOptions)

    fun onHomeActionReceived(item: FragmentActionCommand<Any>)
    fun registerHomeAction(
        pendingAction: FragmentActionCommandResult<Any>,
        callback: (FragmentActionCommandResult<Any>) -> Unit
    )
}