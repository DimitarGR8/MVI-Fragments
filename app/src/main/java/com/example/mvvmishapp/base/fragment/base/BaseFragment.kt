package com.example.mvvmishapp.base.fragment.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.lifecycleScope
import com.example.mvvmishapp.base.fragment.action.BaseAction
import com.example.mvvmishapp.base.fragment.state.BaseViewState
import com.example.mvvmishapp.base.fragment.util.ActivityConfig
import com.example.mvvmishapp.main.MainActivity
import com.example.mvvmishapp.main.custom.extensions.log
import com.example.mvvmishapp.main.custom.extensions.onClick
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

@FlowPreview
abstract class BaseFragment<
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> : Fragment() {

    abstract val viewModelClass: KClass<ViewModel>
    open val activityConfig: ActivityConfig? = null
    private val uiHandler = Handler(Looper.getMainLooper())

    private val viewModel: ViewModel by lazy {
        createViewModelLazy(viewModelClass, { viewModelStore }).value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectStates()
    }

    override fun onDestroy() {
        viewModel.removeHandler()
        uiHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
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

    protected fun postAction(action: Action) {
        viewModel.postAction(action)
    }

    fun View.bindActionOnClick(action: () -> Action) {
        hideKeyboard()
        onClick {
            postAction(action.invoke())
        }
    }

    open fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    open fun onDraw(view: View, lastState: ViewStateData?) {
        activityConfig?.let {
            (requireActivity() as MainActivity).reflectActivityConfig(it)
        }
    }

    open fun backButtonPressed(): Boolean = false
    open fun onKeyboardOpened(isOpen: Boolean) = Unit
    open fun reflectState(state: ViewState) {}
}