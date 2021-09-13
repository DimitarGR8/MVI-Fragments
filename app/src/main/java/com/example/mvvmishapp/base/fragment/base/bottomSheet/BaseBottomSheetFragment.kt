package com.example.mvvmishapp.base.fragment.base.bottomSheet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.lifecycleScope
import com.example.mvvmishapp.R
import com.example.mvvmishapp.base.fragment.base.BaseViewModel
import com.example.mvvmishapp.base.fragment.action.BaseAction
import com.example.mvvmishapp.base.fragment.state.BaseViewState
import com.example.mvvmishapp.main.MainViewModel
import com.example.mvvmishapp.main.custom.extensions.getHeightPercent
import com.example.mvvmishapp.main.custom.extensions.log
import com.example.mvvmishapp.main.custom.extensions.onClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

@FlowPreview
abstract class BaseBottomSheetFragment<
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> : BottomSheetDialogFragment() {

    abstract val viewModelClass: KClass<ViewModel>
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    protected open val peekHeightPercent: Float = 1f
    protected open val heightPercent: Float = 1f

    protected fun dismissBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        collectStates()
    }

    private fun customizeDialog() {
        dialog?.setOnShowListener {
            val dialog = dialog as BottomSheetDialog
            val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val coordinatorLayout = bottomSheet.parent as? CoordinatorLayout
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

                bottomSheetBehavior.peekHeight = getHeightPercent(peekHeightPercent)
                bottomSheet.updateLayoutParams {
                    height = getHeightPercent(heightPercent)
                }
                coordinatorLayout?.parent?.requestLayout()
            }
        }
    }

    private fun collectStates() {
        viewModel.restoreStateSubject.filter {
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
        customizeDialog()
        viewModel.setHandler(parentViewModel)
        viewModel.initializeViewState {
            onDraw(view, null)
        }
    }

    open fun View.bindActionOnClick(action: () -> Action) {
        hideKeyboard()
        onClick {
            postAction(action.invoke())
        }
    }

    open fun reflectState(state: ViewState) {}

    open fun onDraw(view: View, lastState: ViewStateData?) {}

    override fun onDestroy() {
        viewModel.removeHandler()
        super.onDestroy()
    }

    open fun View.hideKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}