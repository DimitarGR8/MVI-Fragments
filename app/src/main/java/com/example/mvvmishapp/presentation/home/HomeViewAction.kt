package com.example.mvvmishapp.presentation.home

import com.example.mvvmishapp.base.activity.commands.FragmentActionCommandResult
import com.example.mvvmishapp.base.activity.commands.PopBackStackCommand
import com.example.mvvmishapp.base.activity.commands.RegisterFragmentActionCommand
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationOptions
import com.example.mvvmishapp.base.fragment.action.BaseAction
import com.example.mvvmishapp.domain.model.other.ExampleActionCommand
import kotlinx.coroutines.FlowPreview

@FlowPreview
sealed class HomeViewAction : BaseAction<HomeViewData> {

    object OnButtonClicked : HomeViewAction()

    //If we have to pass data within fragments we should use the arguments provided in the navigation options
    //However we user action commands to pass data when going back and popping the backstack
    class OnBackPressed(override val upTo: NavigationOptions? = null) : HomeViewAction(),
        PopBackStackCommand

    class RegisterActionCommand(
        override val pendingAction: FragmentActionCommandResult<ExampleActionCommand> = OnMediaActionReceived()
    ) : HomeViewAction(), RegisterFragmentActionCommand<ExampleActionCommand>

    class OnMediaActionReceived(
        override val fragmentTag: String = NavigationOptions.HomeFragmentOption().fragmentTag,
        override var data: ExampleActionCommand? = null,
        override val type: String = ExampleActionCommand::class.java.simpleName
    ) : HomeViewAction(), FragmentActionCommandResult<ExampleActionCommand> {
        override fun stateReducer(previousData: HomeViewData): HomeViewData {
            return previousData.copy(clickedUserData = previousData.clickedUserData)
        }
    }
}