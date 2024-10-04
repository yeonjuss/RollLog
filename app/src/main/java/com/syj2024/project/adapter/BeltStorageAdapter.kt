package com.syj2024.project.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.syj2024.project.R
import com.syj2024.project.activity.BeltStorageItme
import com.syj2024.project.databinding.RecyclerItemListBeltActivityBinding
import java.util.Calendar

class BeltStorageAdapter constructor(val context: Context , val beltList: MutableList<BeltStorageItme>) : Adapter<BeltStorageAdapter.ViewHolder> () {
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

   inner class ViewHolder(var binding: RecyclerItemListBeltActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeltStorageAdapter.ViewHolder {

        val binding= RecyclerItemListBeltActivityBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BeltStorageAdapter.ViewHolder, position: Int) {

        val belt = beltList.get(position)

        holder.binding.tvGrau.text = belt.grau
        holder.binding.tvDate.text = belt.date



    }

    override fun getItemCount(): Int {
        return  beltList.size
    }
}