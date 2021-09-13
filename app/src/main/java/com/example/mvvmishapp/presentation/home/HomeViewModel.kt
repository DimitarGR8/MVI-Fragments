package com.example.mvvmishapp.presentation.home

import com.example.mvvmishapp.base.fragment.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    //If any repositories have to be added
)  : BaseViewModel<HomeViewAction, HomeViewState, HomeViewData>() {

    override var viewStateData: HomeViewData = HomeViewData()

    override fun onInitialBind() {
        super.onInitialBind()

        //On initialising the view model make api call to receive list and after receiving it pass it to fragment
        postState(HomeViewState.OnHomeListReceived(emptyList()))

        //Register the action which will accept the data from the fragment that was on top of this one and then closed(poppped)
        postAction(HomeViewAction.RegisterActionCommand())
    }

    override fun onActionReceived(action: HomeViewAction): Flow<HomeViewState> {
        return when(action) {
            is HomeViewAction.OnButtonClicked -> {
                //Do something on button click

                consumedAction()
            }
            is HomeViewAction.OnMediaActionReceived -> {
                //Here we accept the data that we have received from the fragment that was opened on top of this one and then closed(poppped).
                viewStateData.clickedUserData

                consumedAction()
            }
            else -> consumedAction()
        }
    }
}