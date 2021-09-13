package com.example.mvvmishapp.base.activity.commands

import com.example.mvvmishapp.base.activity.commands.navigation.NavigationOptions
import kotlinx.coroutines.FlowPreview

@FlowPreview
interface PopBackStackCommand {
    val upTo: NavigationOptions?
}