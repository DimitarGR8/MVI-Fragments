package com.example.mvvmishapp.presentation.home.lists

import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmishapp.databinding.RecyclerviewHomeRowBinding
import com.example.mvvmishapp.main.custom.extensions.onClick

class HomeFragmentViewHolder(
    private val viewBinding: RecyclerviewHomeRowBinding,
    private val onCountryCodeRowClicked: (String) -> Unit) : RecyclerView.ViewHolder(viewBinding.root) {

    fun setRowData(rowData: String) {
        //Set views as well here

        viewBinding.root.onClick {
            onCountryCodeRowClicked.invoke(rowData)
        }
    }
}