package com.example.mvvmishapp.base.fragment.base.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.lifecycleScope
import com.example.mvvmishapp.base.fragment.base.BaseViewModel
import com.example.mvvmishapp.base.fragment.action.BaseAction
import com.example.mvvmishapp.base.fragment.state.BaseViewState
import com.example.mvvmishapp.main.MainViewModel
import com.example.mvvmishapp.main.custom.extensions.getHeightPercent
import com.example.mvvmishapp.main.custom.extensions.getWidthPercent
import com.example.mvvmishapp.main.custom.extensions.log
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

@FlowPreview
abstract class BaseDialogFragment<
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> : DialogFragment() {

    abstract val viewModelClass: KClass<ViewModel>

    protected open val widthPercent: Float = 1f
    protected open val heightPercent: Float = 1f

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(getWidthPercent(widthPercent), getHeightPercent(heightPercent))
    }

    private val viewModel: ViewModel by lazy {
        createViewModelLazy(viewModelClass, { viewModelStore }).value
    }

    private val parentViewModel: MainViewModel by activityViewModels()

    protected fun postAction(action: Action) {
        viewModel.postAction(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectStates()
    }

    private fun collectStates() {
        viewModel.restoreStateSubject
            .filter {
                view != null
            }.onEach {
                onDraw(requireView(), it)
            }.launchIn(lifecycleScope)
        viewModel.stateSubject
            .filter {
                view != null
            }.onEach {
                "${this::class.java.simpleName} - State - ${it.javaClass.simpleName}".log("MVI")
                reflectState(it)
            }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setHandler(parentViewModel)
        viewModel.initializeViewState {
            onDraw(view, null)
        }
    }

    open fun reflectState(state: ViewState) {}

    open fun onDraw(view: View, lastState: ViewStateData?) {}

    override fun onDestroy() {
        viewModel.removeHandler()
        super.onDestroy()
    }

}
