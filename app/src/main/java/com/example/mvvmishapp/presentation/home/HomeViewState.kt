package com.example.mvvmishapp.presentation.home

import com.example.mvvmishapp.base.fragment.state.BaseViewState

sealed class HomeViewState : BaseViewState<HomeViewData> {

    class OnHomeListReceived(val newDataSet: List<String>) : HomeViewState() {
        override fun stateReducer(previousData: HomeViewData): HomeViewData {
            return previousData.copy(dataSet = newDataSet)
        }
    }
}