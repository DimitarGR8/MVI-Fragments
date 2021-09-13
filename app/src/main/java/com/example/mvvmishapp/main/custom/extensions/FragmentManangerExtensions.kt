package com.example.mvvmishapp.main.custom.extensions

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.transition.TransitionInflater
import com.example.mvvmishapp.R
import com.example.mvvmishapp.base.activity.commands.navigation.NavigationOptions
import kotlinx.coroutines.FlowPreview

@FlowPreview
fun FragmentManager.drawFragment(navOptions: NavigationOptions, context: Context) {
    commit(allowStateLoss = true) {

        if (navOptions.popBackStack != null)
            when (navOptions.popBackStack) {
                is PopBackStack.Last -> {
                    popBackStack()
                }
                is PopBackStack.Clear -> {
                    popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                is PopBackStack.From -> {
                    popBackStack((navOptions.popBackStack as PopBackStack.From).name, 0)
                }
            }


        var fragment = navOptions.fragmentCreator!!.invoke()

        if (navOptions.sharedElement != null) {
            fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.image_transition)
            fragment.sharedElementReturnTransition = null
            val transitionName = navOptions.sharedElement!!.transitionName
            fragment.saveTransitionName(transitionName)
            addSharedElement(navOptions.sharedElement!!, transitionName)
        }

        if (navOptions.argument != null) {
            fragment = fragment.apply {
                (this as Fragment).arguments =
                    bundleOf(navOptions.fragmentTag to navOptions.argument)
            }
        }

        replace(R.id.fragmentHolder, fragment, navOptions.fragmentTag)

        if (navOptions.addToBackStack) {
            addToBackStack(navOptions.fragmentTag)
        }
    }
}

const val TRANSITION_NAME_KEY = "TRANSITION_NAME_KEY"

fun Fragment.saveTransitionName(name: String) {
    if (arguments == null)
        arguments = bundleOf(TRANSITION_NAME_KEY to name)
    else
        requireArguments().putString(TRANSITION_NAME_KEY, name)
}

fun Fragment.setSavedTransitionName(targetView: View) {
    arguments?.getString(TRANSITION_NAME_KEY)?.let {
        targetView.transitionName = it
    }
}

sealed class PopBackStack {
    object Last : PopBackStack()
    object Clear : PopBackStack()
    class From(val name: String) : PopBackStack()
}

@FlowPreview
fun FragmentManager.drawDialogFragment(item: NavigationOptions) {
    item.dialogCreator?.invoke()?.show(this, item.fragmentTag)
}

@FlowPreview
fun FragmentManager.drawBottomSheetFragment(item: NavigationOptions) {
    item.bottomSheetCreator?.invoke().apply {
        if (item.argument != null) {
            (this as Fragment).arguments = bundleOf(item.fragmentTag to item.argument)
        }
    }?.show(this, item.fragmentTag)
}
