package com.example.mvvmishapp.base.fragment.action

interface BaseAction<Data> {
    fun stateReducer(previousData: Data): Data = previousData
}
