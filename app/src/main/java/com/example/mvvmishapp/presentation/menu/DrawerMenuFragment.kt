package com.example.mvvmishapp.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmishapp.base.fragment.base.BaseFragment
import com.example.mvvmishapp.databinding.FragmentDrawerMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class DrawerMenuFragment:
    BaseFragment<DrawerMenuViewAction, DrawerMenuViewState, DrawerMenuViewModel, DrawerMenuViewData>() {

    private var binding: FragmentDrawerMenuBinding? = null
    override val viewModelClass = DrawerMenuViewModel::class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerMenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun reflectState(state: DrawerMenuViewState) {
        super.reflectState(state)
    }

    override fun onDraw(view: View, lastState: DrawerMenuViewData?) {
        super.onDraw(view, lastState)

        if(lastState != null) {
            // Initialise views with previous data
        }

        initViews()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun initViews() {
        // Initialise views
    }
}