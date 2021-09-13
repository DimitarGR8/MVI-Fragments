package com.example.mvvmishapp.presentation.menu

import com.example.mvvmishapp.base.fragment.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrawerMenuViewModel  @Inject constructor(
    //If any repositories have to be added
)  : BaseViewModel<DrawerMenuViewAction, DrawerMenuViewState, DrawerMenuViewData>() {

    override var viewStateData: DrawerMenuViewData = DrawerMenuViewData()
}