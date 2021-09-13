package com.example.mvvmishapp.base.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmishapp.base.activity.handlers.MainHandler
import com.example.mvvmishapp.databinding.ActivityMainBinding
import com.example.mvvmishapp.main.MainViewModel
import kotlinx.coroutines.FlowPreview

@FlowPreview
abstract class BaseActivity : AppCompatActivity(), MainHandler {

    protected val viewModel: MainViewModel by viewModels()
    protected lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel.bindView(this)
    }

    override fun onDestroy() {
        viewModel.unbindView()
        super.onDestroy()
    }
}