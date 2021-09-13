package com.example.mvvmishapp.base.activity.commands.navigation

import android.view.View
import com.example.mvvmishapp.base.fragment.base.BaseFragment
import com.example.mvvmishapp.base.fragment.base.bottomSheet.BaseBottomSheetFragment
import com.example.mvvmishapp.base.fragment.base.dialog.BaseDialogFragment
import com.example.mvvmishapp.main.custom.extensions.PopBackStack
import com.example.mvvmishapp.presentation.authentication.LoginFragment
import com.example.mvvmishapp.presentation.home.HomeFragment
import kotlinx.coroutines.FlowPreview
import java.io.Serializable

@FlowPreview
sealed class NavigationOptions(
    open val fragmentTag: String,
    open val addToBackStack: Boolean,
    open var argument: Serializable? = null,
    open val sharedElement: View? = null,
    open val popBackStack: PopBackStack? = null,
    open val pushAnimation: Boolean,
    open val fragmentCreator: (() -> BaseFragment<*, *, *, *>)? = null,
    open val dialogCreator: (() -> BaseDialogFragment<*, *, *, *>)? = null,
    open val bottomSheetCreator: (() -> BaseBottomSheetFragment<*, *, *, *>)? = null
    ) {

    class LoginFragmentOption(override var addToBackStack: Boolean = true,
                              override var popBackStack: PopBackStack? = null) : NavigationOptions(
        fragmentTag = "LoginFragment",
        fragmentCreator = { LoginFragment() },
        addToBackStack = addToBackStack,
        pushAnimation = false,
        popBackStack = popBackStack
    )


    class HomeFragmentOption(override var addToBackStack: Boolean = true,
                              override var popBackStack: PopBackStack? = null) : NavigationOptions(
        fragmentTag = "HomeFragment",
        fragmentCreator = { HomeFragment() },
        addToBackStack = addToBackStack,
        pushAnimation = false,
        popBackStack = popBackStack
    )
}