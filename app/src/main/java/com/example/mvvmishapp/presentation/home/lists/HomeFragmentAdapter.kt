package com.example.mvvmishapp.presentation.home.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmishapp.databinding.RecyclerviewHomeRowBinding

class HomeFragmentAdapter(private val onCountryCodeRowClicked: (String) -> Unit) : RecyclerView.Adapter<HomeFragmentViewHolder>() {
    private var myDataSet: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        val viewBinding = RecyclerviewHomeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeFragmentViewHolder(viewBinding, onCountryCodeRowClicked)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        if (!myDataSet.indices.contains(position)) {
            return
        }

        val rowData = myDataSet[position]
        holder.setRowData(rowData)
    }

    override fun getItemCount(): Int {
        return myDataSet.count()
    }

    fun updateDataSet(myDataSet: List<String>) {
        this.myDataSet = myDataSet
        this.notifyDataSetChanged()
    }
}