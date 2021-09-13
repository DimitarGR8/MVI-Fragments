package com.example.mvvmishapp.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.example.mvvmishapp.R
import com.example.mvvmishapp.base.activity.BaseActivity
import com.example.mvvmishapp.base.activity.commands.FragmentActionCommand
import com.example.mvvmishapp.base.activity.commands.FragmentActionCommandResult
import com.example.mvvmishapp.base.activity.commands.FragmentActionObservers
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationOptions
import com.example.mvvmishapp.base.fragment.base.BaseFragment
import com.example.mvvmishapp.base.fragment.util.ActivityConfig
import com.example.mvvmishapp.domain.model.enums.AuthSessionStatus
import com.example.mvvmishapp.main.custom.extensions.*
import com.example.mvvmishapp.main.custom.managers.DialogOptions
import com.example.mvvmishapp.main.custom.managers.DialogsManager
import com.example.mvvmishapp.main.custom.managers.SystemServices
import com.example.mvvmishapp.main.custom.managers.keyboard.KeyboardManager
import com.example.mvvmishapp.main.custom.views.OfflineWarningDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var initialNavigationOption: NavigationOptions
    private lateinit var keyboardManager: KeyboardManager

    private val homeActionObservers: FragmentActionObservers = mutableListOf()
    private var lastKeyboardHeight = 0
    private var splashScreenHandler = Handler(Looper.getMainLooper())
    private var offlineWarningDialog: OfflineWarningDialog? = null

    private val initialNavigationRunnable = Runnable {
        //Splash screen has to be handled here if there is one

        viewBinding.layoutMain.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = resources.getStatusBarHeight()
        }
        executeNavigation(initialNavigationOption)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.drawUnderStatusBar()

        viewModel.initializeSessionStatus(intent.action)

        initializeManagers()
        registerBackStackListener()
        registerConnectivityListener()
    }

    override fun onHomeActionReceived(item: FragmentActionCommand<Any>) {
        homeActionObservers.filter {
            it.first.type == item.type
        }.forEach {
            it.first.data = item.data
            it.second.invoke(it.first)
        }
    }

    override fun registerHomeAction(
        pendingAction: FragmentActionCommandResult<Any>,
        callback: (FragmentActionCommandResult<Any>) -> Unit) {
        homeActionObservers.add(pendingAction to callback)
    }

    override fun initAppSession(status: AuthSessionStatus) {
        initialNavigationOption = when (status) {
            AuthSessionStatus.Authorized -> {
                NavigationOptions.HomeFragmentOption()
            }
            AuthSessionStatus.Unauthorized -> {
                NavigationOptions.LoginFragmentOption()
            }
        }

        val splashDuration = 500.toLong()
        splashScreenHandler.postDelayed(initialNavigationRunnable, splashDuration)
    }

    override fun popBackStack(upTo: NavigationOptions?) {
        when {
            upTo == null -> supportFragmentManager.popBackStack()
            supportFragmentManager.findFragmentByTag(upTo.fragmentTag) == null ->
                executeNavigation(upTo)
            else -> supportFragmentManager.popBackStack(upTo.fragmentTag, 0)
        }
    }

    override fun onBackPressed() {
        val isActionConsumed = getLastFragment()?.backButtonPressed() ?: false
        if (!isActionConsumed)
            when {
                viewBinding.menuDrawer.isDrawerOpen(GravityCompat.START) -> {
                    closeDrawer()
                }
                supportFragmentManager.backStackEntryCount == 1 -> {
                    moveTaskToBack(true)
                }
                else -> {
                    super.onBackPressed()
                }
            }
    }

    override fun executeNavigation(item: NavigationOptions) {
        when {
            item.fragmentCreator != null -> supportFragmentManager.drawFragment(item, this)
            item.dialogCreator != null -> supportFragmentManager.drawDialogFragment(item)
            item.bottomSheetCreator != null -> supportFragmentManager.drawBottomSheetFragment(item)
        }
    }

    override fun showWarning(message: DialogOptions) {
        DialogsManager.showSnackBar(this, message)
    }

    override fun onStart() {
        super.onStart()

        keyboardManager.setKeyboardHeightObserver { h, _ ->
            onKeyboardHeightChange(h)
        }
    }

    override fun onStop() {
        super.onStop()
        keyboardManager.setKeyboardHeightObserver(null)
        onKeyboardHeightChange(0)
    }

    override fun onDestroy() {
        splashScreenHandler.removeCallbacks(initialNavigationRunnable)
        keyboardManager.close()
        super.onDestroy()
    }

    private fun initializeManagers() {
        initializeKeyboardManager()
    }

    private fun registerConnectivityListener() {
        SystemServices.getNetworkService(this).checkNetworkConnection { isConnected ->
            offlineWarningDialog?.dismiss()
            offlineWarningDialog = null
            if (!isConnected) {
                showOfflineWarning()
            }
        }
    }

    private fun showOfflineWarning() {
        offlineWarningDialog = OfflineWarningDialog(this).showDialog()
    }

    private fun closeDrawer() {
        viewBinding.menuDrawer.closeDrawers()
    }

    private fun onKeyboardHeightChange(h: Int) {
        if (lastKeyboardHeight != h) {
            viewBinding.fragmentHolder.updatePadding(bottom = h)
            queryAllFragments {
                onKeyboardOpened(h > 0)
            }
            lastKeyboardHeight = h
        }
    }

    private fun queryAllFragments(callback: BaseFragment<*, *, *, *>.() -> Unit) {
        supportFragmentManager.fragments.filterIsInstance<BaseFragment<*, *, *, *>>().forEach {
            it.callback()
        }
    }

    private fun getLastFragment(): BaseFragment<*, *, *, *>? {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentHolder)
        return if (fragment is BaseFragment<*, *, *, *>)
            fragment
        else
            null
    }

    private fun registerBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            syncHomeActionObservers()
        }
    }

    private fun syncHomeActionObservers() {
        homeActionObservers.removeAll { observer ->
            val remove =
                supportFragmentManager.findFragmentByTag(observer.first.fragmentTag) == null
            remove
        }
    }

    private fun initializeKeyboardManager() {
        keyboardManager = KeyboardManager(this)
        viewBinding.root.post {
            keyboardManager.start()
        }
    }

    private fun setupWindowBackground(activityConfig: ActivityConfig) {
        val isLight = activityConfig == ActivityConfig.BaseWhiteBackground
        window.setStatusBarBackground(isLightBackground = isLight)
    }

    fun reflectActivityConfig(activityConfig: ActivityConfig) {
        setupWindowBackground(activityConfig)
    }
}