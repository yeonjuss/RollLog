package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.syj2024.project.R
import com.syj2024.project.activity.LogActivity
import com.syj2024.project.databinding.RecyclerItemListLogfragmentBinding
import com.syj2024.project.fragment.Item
import com.syj2024.project.fragment.Item2
import com.syj2024.project.fragment.LogListFragment
import okhttp3.internal.immutableListOf

class LogListAdapter (val context: Context,val logList: MutableList<Item2>) : Adapter<LogListAdapter.VH2>(){

    inner class VH2(var binding:RecyclerItemListLogfragmentBinding) : ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogListAdapter.VH2 {

        val binding =RecyclerItemListLogfragmentBinding.inflate(LayoutInflater.from(context),parent,false)
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


        // 이미지 설정 (photoList가 비어있지 않고, 첫 번째 이미지가 null이 아닐 경우)
        if (log.photoList.isNotEmpty() && log.photoList[0] != null ) {
            val firstImageUri = log.photoList[0]
            // photoList[0]가 콤마로 구분된 URI 문자열일 경우 첫 번째 URI만 분리
//            val firstImageUriString = log.photoList[0].toString().split(",")[0] // 첫 번째 이미지 URI 가져오기
//            val firstImageUri = Uri.parse(firstImageUriString)

            holder.binding.cv.visibility = View.VISIBLE
            Glide.with(context)
                  .load(firstImageUri)
                   .override(140, 200)  // 필요한 크기로 축소
                  .diskCacheStrategy(DiskCacheStrategy.ALL)  // 디스크 캐시 사용 , 이미지가 너무 클 경우, 캐시를 사용하여 이미지를 불러오는 성능을 개선
                  .placeholder(R.drawable.ic_action_image)
                  .error(R.drawable.ic_action_rc)
                  .into(holder.binding.iv)
        } else {
//            holder.binding.iv.setImageResource(R.drawable.ic_action_image)
              holder.binding.cv.visibility = View.GONE
        }


       // 삭제 버튼 눌렀을때 삭제 확인 다이얼로그 띄우기
        holder.binding.ivDelete.setOnClickListener {
            showDeleteConfirmationDialog(log,position)

        }

        // 항목 클릭 시 LogActivity로 이동
        holder.itemView.setOnClickListener {
            val intent = Intent(context, LogActivity::class.java).apply {

                putExtra("date",log.date)
                putExtra("title",log.title)
                putExtra("event",log.event)
//              putExtra("photo", log.photoList.joinToString(","))

                // Uri list를 ArrayList로 변환하여 전달
              putParcelableArrayListExtra("photoList", ArrayList(log.photoList.filterNotNull()))



            }
            context.startActivity(intent)

        }



    }

    private fun removeList(log: Item2, position: Int) {

            val db: SQLiteDatabase =
                context.openOrCreateDatabase("data", Context.MODE_PRIVATE, null)

            db.execSQL("DELETE FROM log WHERE id=?", arrayOf(log.id.toString()))
            db.close()

            // 리스트에서 아이템 삭제
            logList.removeAt(position)

            // 어댑터에 데이터가 변경되었음을 알림
            notifyItemRemoved(position)


        }
    private fun showDeleteConfirmationDialog(log: Item2 ,position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("리스트 삭제")
        builder.setMessage("정말로 삭제하시겠습니까?")

        // 확인 버튼
        builder.setPositiveButton("확인") { dialog, _ ->
            removeList(log, position) // 아이템 삭제
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


