package com.example.mvvmishapp.presentation.home

import com.example.mvvmishapp.domain.model.other.ExampleActionCommand

data class HomeViewData(
    val dataSet: List<String> ?= emptyList(),
    val clickedUserData: ExampleActionCommand ?= null
)