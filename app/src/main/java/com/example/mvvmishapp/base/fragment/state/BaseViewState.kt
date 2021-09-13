package com.example.mvvmishapp.base.fragment.state

interface BaseViewState<Data> {
    fun stateReducer(previousData: Data): Data = previousData
}
