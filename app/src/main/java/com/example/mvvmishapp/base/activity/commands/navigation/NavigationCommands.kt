package com.example.mvvmishapp.base.activity.commands.navigation

import kotlinx.coroutines.FlowPreview

@FlowPreview
interface NavigationCommands {
    val option: NavigationOptions
}