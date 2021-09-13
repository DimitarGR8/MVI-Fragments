package com.example.mvvmishapp.base.activity.commands

interface RegisterFragmentActionCommand<T> {
    val pendingAction: FragmentActionCommandResult<T>
}

interface FragmentActionCommandResult<T> {
    var data: T?
    val fragmentTag: String?
    val type: String
}

interface FragmentActionCommand<T> {
    var data: T?
    val type: String
}

typealias FragmentActionObservers = MutableList<Pair<FragmentActionCommandResult<Any>, (FragmentActionCommandResult<Any>) -> Unit>>
