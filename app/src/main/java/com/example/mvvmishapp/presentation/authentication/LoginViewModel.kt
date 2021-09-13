package com.example.mvvmishapp.presentation.authentication

import com.example.mvvmishapp.base.fragment.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class LoginViewModel @Inject constructor(

    ): BaseViewModel<LoginViewAction, LoginViewState, LoginViewData>() {

    override var viewStateData: LoginViewData = LoginViewData()
}