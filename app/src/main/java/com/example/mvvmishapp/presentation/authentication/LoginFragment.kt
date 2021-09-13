package com.example.mvvmishapp.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmishapp.base.fragment.base.BaseFragment
import com.example.mvvmishapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewAction, LoginViewState, LoginViewModel, LoginViewData>() {

    private var binding: FragmentLoginBinding? = null
    override val viewModelClass = LoginViewModel::class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDraw(view: View, lastState: LoginViewData?) {
        super.onDraw(view, lastState)

        initViews()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun initViews() {

    }
}