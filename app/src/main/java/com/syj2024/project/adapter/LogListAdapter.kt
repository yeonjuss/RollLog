package com.syj2024.project.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.syj2024.project.R
import com.syj2024.project.databinding.RecyclerItemListLogfragmentBinding
import com.syj2024.project.fragment.Item
import com.syj2024.project.fragment.Item2
import com.syj2024.project.fragment.LogListFragment

class LogListAdapter (val context: Context, val logList: List<Item2>) : Adapter<LogListAdapter.VH2>() {

    inner class VH2(var binding:RecyclerItemListLogfragmentBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH2 {
      val binding= RecyclerItemListLogfragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH2(binding)
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    override fun onBindViewHolder(holder: VH2, position: Int) {
        val log: Item2 = logList[position]

        holder.binding.tvDate.text=log.date
        holder.binding.tvTitle.text=log.title
        holder.binding.tvLog.text=log.event


    } // bindViewholder



    // "data.db"라는 이름의 데이터베이스 파일 열기(생성)하기
    val db:SQLiteDatabase=context.openOrCreateDatabase("data", Context.MODE_PRIVATE,null)

    // "log"라는 이름의 테이블(표) 만들기





}



