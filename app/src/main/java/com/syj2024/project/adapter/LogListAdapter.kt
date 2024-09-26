package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.syj2024.project.R
import com.syj2024.project.activity.LogActivity
import com.syj2024.project.databinding.RecyclerItemListLogfragmentBinding
import com.syj2024.project.fragment.Item
import com.syj2024.project.fragment.Item2

class LogListAdapter (val context: Context,val logList: List<Item2>) : Adapter<LogListAdapter.VH2>(){

    inner class VH2(var binding:RecyclerItemListLogfragmentBinding) : ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH2 {

        val binding=RecyclerItemListLogfragmentBinding.inflate(LayoutInflater.from(context),parent,false)
            return VH2(binding)
    }

    override fun getItemCount(): Int {
        return  logList.size
    }

    override fun onBindViewHolder(holder: VH2, position: Int) {

        val log = logList.get(position)


        holder.binding.tvDate.text = log.date
        holder.binding.tvTitle.text = log.title
        holder.binding.tvEvent.text = log.event



        holder.binding.ivDelete.setOnClickListener {
            showDeleteConfirmationDialog(log)

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, LogActivity::class.java).apply {

                putExtra("date",log.date)
                putExtra("title",log.title)
                putExtra("event",log.event)

            }
            context.startActivity(intent)

        }

    }

        fun removeList(log:Item2) {

            val db:SQLiteDatabase= context.openOrCreateDatabase("data",Context.MODE_PRIVATE,null)

            db.execSQL("DELETE FROM log WHERE title=?", arrayOf(log.title))
            db.close()

            notifyItemRemoved(logList.size)
            notifyItemRangeChanged(itemCount,logList.size)


        }

    private fun showDeleteConfirmationDialog(log: Item2) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("리스트 삭제")
        builder.setMessage("정말로 삭제하시겠습니까?")

        // 확인 버튼
        builder.setPositiveButton("삭제") { dialog, _ ->
            removeList(log) // 아이템 삭제
            dialog.dismiss()
        }
        // 취소 버튼
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss() // 대화상자 닫기
        }
        // AlertDialog 보여주기
        val alertDialog = builder.create()
        alertDialog.show()
    }




}


