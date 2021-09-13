package com.example.mvvmishapp.main

import androidx.lifecycle.viewModelScope
import com.example.mvvmishapp.base.activity.BaseActivityViewModel
import com.example.mvvmishapp.base.activity.commands.*
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationCommands
import com.example.mvvmishapp.domain.model.enums.AuthSessionStatus
import com.example.mvvmishapp.domain.repository.CredentialsRepository
import com.example.mvvmishapp.main.custom.extensions.generateRandomString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(
    private val credentialsRepository: CredentialsRepository
    ) : BaseActivityViewModel() {

    override fun onBind() {
        viewModelScope.launch(Dispatchers.IO) {
            initializeDeviceToken()
        }
    }

    override fun popBackStack(command: PopBackStackCommand) {
        requireHandler {
            it.popBackStack(command.upTo)
        }
    }

    override fun showWarning(command: WarningCommand) {
        requireHandler {
            it.showWarning(command.message)
        }
    }

    override fun initializeSessionStatus(
        action: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            //Check for authenticationToken
            val isAuthenticated = false

            val sessionStatus = when {
                isAuthenticated ->
                    AuthSessionStatus.Unauthorized
                else ->
                    AuthSessionStatus.Authorized
            }
            withContext(Dispatchers.Main) {
                requireHandler {
                    it.initAppSession(sessionStatus)
                }
            }
        }
    }

    override fun executeNavigation(command: NavigationCommands) {
        requireHandler { handler ->
            handler.executeNavigation(command.option)
        }
    }

    override fun onFragmentActionReceived(fragmentActionCommandResult: FragmentActionCommand<Any>) {
        if (fragmentActionCommandResult.data != null)
            requireHandler {
                it.onHomeActionReceived(fragmentActionCommandResult)
            }
    }

    override fun registerFragmentAction(
        fragmentAction: RegisterFragmentActionCommand<Any>,
        callback: (FragmentActionCommandResult<Any>) -> Unit
    ) {
        requireHandler {
            it.registerHomeAction(fragmentAction.pendingAction, callback)
        }
    }

    private suspend fun initializeDeviceToken() {
        val previousDeviceId = credentialsRepository.getDeviceId()
        if (previousDeviceId.isEmpty()) {
            val deviceId = generateRandomString()
            credentialsRepository.saveDeviceID(deviceId)
        }
    }
}