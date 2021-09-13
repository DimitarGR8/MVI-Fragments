package com.example.mvvmishapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmishapp.base.fragment.base.BaseFragment
import com.example.mvvmishapp.databinding.FragmentHomeBinding
import com.example.mvvmishapp.presentation.home.lists.HomeFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewAction, HomeViewState, HomeViewModel, HomeViewData>() {

    private var binding: FragmentHomeBinding? = null
    override val viewModelClass = HomeViewModel::class

    private lateinit var countryCodesAdapter: HomeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun reflectState(state: HomeViewState) {
        when(state) {
            is HomeViewState.OnHomeListReceived -> {
                //Here adapter`s list could be updated after successful api call
                countryCodesAdapter.updateDataSet(state.newDataSet)
            }
        }
    }

    override fun onDraw(view: View, lastState: HomeViewData?) {
        super.onDraw(view, lastState)


        if(lastState != null) {
            // Set old views data
        }

        initViews()
        setupAdapter()
    }

    private fun initViews() {
        //Set views data
        //Example action command triggered
        binding?.btTestButton?.bindActionOnClick {
            HomeViewAction.OnBackPressed()
        }
    }

    private fun setupAdapter() {
        binding?.rvHomeList?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        countryCodesAdapter = HomeFragmentAdapter {
            //Do something on row or button click
            postAction(HomeViewAction.OnButtonClicked)
        }
        binding?.rvHomeList?.adapter = countryCodesAdapter
        binding?.rvHomeList?.isNestedScrollingEnabled = true
    }

    override fun onDestroy() {
        binding =  null
        super.onDestroy()
    }


}