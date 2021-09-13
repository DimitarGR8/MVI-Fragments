package com.example.mvvmishapp.base.fragment.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmishapp.base.activity.commands.PopBackStackCommand
import com.example.mvvmishapp.base.activity.commands.WarningCommand
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationCommands
import com.example.mvvmishapp.base.activity.handlers.CommandHandler
import com.example.mvvmishapp.base.fragment.action.BaseAction
import com.example.mvvmishapp.base.fragment.state.BaseViewState
import com.example.mvvmishapp.domain.api.response.BaseResponse
import com.example.mvvmishapp.main.custom.extensions.log
import com.example.mvvmishapp.main.custom.managers.DialogOptions
import com.example.mvvmishapp.main.errors.ApiException
import com.example.mvvmishapp.main.errors.getErrorMessage
import com.example.mvvmishapp.main.errors.getMessage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Response

@FlowPreview
abstract class BaseViewModel<
        ViewAction : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewStateData> : ViewModel() {

    abstract var viewStateData: ViewStateData

    private var isViewInitialized = false

    val stateSubject: MutableSharedFlow<ViewState> = MutableSharedFlow()

    val restoreStateSubject: MutableSharedFlow<ViewStateData> = MutableSharedFlow()

    private val actionSubject: MutableSharedFlow<ViewAction> = MutableSharedFlow()

    init {
        collectActions()
    }

    private var commandHandler: CommandHandler? = null

    fun setHandler(commandHandler: CommandHandler) {
        this.commandHandler = commandHandler
    }

    fun removeHandler() {
        this.commandHandler = null
    }

    private fun collectActions() {
        actionSubject.flatMapMerge {
            viewStateData = it.stateReducer(viewStateData)
            processAction(it)
        }.map {
            viewStateData = it.stateReducer(viewStateData)
            it
        }.onEach {
            stateSubject.emit(it)
        }.launchIn(viewModelScope)
    }

    fun postAction(action: ViewAction) {
        viewModelScope.launch {
            actionSubject.emit(action)
        }
    }

    fun postState(state: ViewState) {
        viewModelScope.launch {
            viewStateData = state.stateReducer(viewStateData)
            stateSubject.emit(state)
        }
    }

    fun initializeViewState(initialize: () -> Unit) {
        if (isViewInitialized) {
            execute {
                restoreStateSubject.emit(viewStateData)
            }
        } else {
            initialize.invoke()
            onInitialBind()
        }
        isViewInitialized = true
    }

    protected fun execute(
        withLoader: Boolean = false,
        callback: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (withLoader)
                // If loader has to be implemented within the app
            try {
                if (withLoader)
                    delay(500)
                this.callback()
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    val command = object : WarningCommand {
                        override val message: DialogOptions = exception.getMessage()
                    }
                    Log.d("apiResponseException: ", exception.toString())
                    //todo logic to be added
                    commandHandler?.showWarning(command)
                }
            } finally {
                if (withLoader) {
                    //toggleLoader(false)
                }
            }
        }
    }

    private fun processAction(viewAction: ViewAction): Flow<ViewState> {
        "${this::class.java.simpleName} - Action - ${viewAction.javaClass.simpleName}".log("MVI")

        return when (viewAction) {
            is PopBackStackCommand -> {
                commandHandler?.popBackStack(viewAction)
                emptyFlow()
            }
            is NavigationCommands -> {
                commandHandler?.executeNavigation(viewAction)
                emptyFlow()
            }
            else -> onActionReceived(viewAction)
        }
    }

    open fun onInitialBind() {}

    protected open fun onActionReceived(action: ViewAction): Flow<ViewState> {
        return emptyFlow()
    }

    protected fun consumedAction() = emptyFlow<ViewState>()

    protected fun <T> Response<BaseResponse<T>>.checkResponseWithData(onBadImageRequest: (() -> Unit)? = null) : T {
        val body = body()
        return when {
            isSuccessful -> body!!.data
            else -> {
                if (code() == 422)
                    onBadImageRequest?.invoke()

                throw ApiException(getErrorMessage())
            }
        }

        //todo logic to be added
    }

    protected fun Response<Void>.checkResponse() {
        if (!isSuccessful)
            throw ApiException(getErrorMessage())
        //todo logic to be added
    }
}