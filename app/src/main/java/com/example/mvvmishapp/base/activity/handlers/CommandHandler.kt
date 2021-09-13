package com.example.mvvmishapp.base.activity.handlers

import com.example.mvvmishapp.base.activity.commands.*
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationCommands
import kotlinx.coroutines.FlowPreview
import org.json.JSONObject

@FlowPreview
interface CommandHandler {
    fun initializeSessionStatus(action: String?)
    fun executeNavigation(command: NavigationCommands)
    fun popBackStack(command: PopBackStackCommand)
    fun showWarning(command: WarningCommand)
    fun onFragmentActionReceived(fragmentActionCommandResult: FragmentActionCommand<Any>)
    fun registerFragmentAction(
        fragmentAction: RegisterFragmentActionCommand<Any>,
        callback: (FragmentActionCommandResult<Any>) -> Unit)
}