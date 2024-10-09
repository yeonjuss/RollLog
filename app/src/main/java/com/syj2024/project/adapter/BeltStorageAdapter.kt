package com.syj2024.project.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syj2024.project.R
import com.syj2024.project.activity.BeltStorageItme

import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding
import java.util.Calendar


class BeltStorageAdapter(val beltList: MutableList<BeltStorageItme>, val context: Context) :
    RecyclerView.Adapter<BeltStorageAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: RecyclerItemListBeltstoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 그랄 레이아웃 추가 함수
        fun addGradeLayout(grade: Int) {
            // 새로운 레이아웃 생성
            val gradeLayout = LayoutInflater.from(context).inflate(R.layout.grade_item, null)

            // 각 뷰에 데이터 설정
            val tvGrau = gradeLayout.findViewById<TextView>(R.id.tv_grau)
            val tvDate = gradeLayout.findViewById<TextView>(R.id.tv_date)
            val ivGrauCalendar = gradeLayout.findViewById<ImageView>(R.id.iv_grau_calender)

            // 그랄 수와 날짜 설정
            tvGrau.text = "$grade grau"
            tvDate.text = "date"

            // 부모 LinearLayout에 추가
            binding.gradesContainer.addView(gradeLayout)

            // 새로 추가된 레이아웃의 DatePicker 기능 설정
            ivGrauCalendar.setOnClickListener {
                showDatePickerDialog(tvDate)
            }
        }



        // 각 벨트가 추가될 때 그랄을 0부터 시작하여 4까지 채움
        fun resetGradesForNewBelt(belt: BeltStorageItme) {
            binding.gradesContainer.removeAllViews() // 이전 그랄 레이아웃 초기화
            for (grade in 1..4) { // 0그랄부터 4그랄까지 추가
                addGradeLayout(grade)
            }
        }

        // 각 뷰홀더에 대해 그랄 수 초기화 및 바인딩
        fun bind(belt: BeltStorageItme) {
            binding.tvGrau.text = "${belt.grau} grau"
            binding.tvDate.text = belt.date
            binding.beltImage.setImageResource(getBeltImageResource(belt.color))
            resetGradesForNewBelt(belt) // 벨트를 추가할 때 그랄 초기화
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeltStorageAdapter.ViewHolder {
        val binding = RecyclerItemListBeltstoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return beltList.size
    }

    override fun onBindViewHolder(holder: BeltStorageAdapter.ViewHolder, position: Int) {
        val belt = beltList[position]
        holder.bind(belt) // 그랄과 벨트 정보를 뷰홀더에 바인딩

        // 날짜 클릭 리스너 추가
        holder.binding.ivGrauCalender.setOnClickListener {
            showDatePickerDialog(holder.binding.tvDate)
            // DatePickerDialog 코드
        }


    }

    // 새로운 벨트가 추가되면 그랄을 0부터 다시 채울 수 있도록 처리
    fun resetGradesForNewBelt(newBelt: BeltStorageItme) {
        val position = beltList.indexOf(newBelt)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    private fun getBeltImageResource(color: String): Int {
        return when (color) {
            "White" -> R.drawable.white
            "Blue" -> R.drawable.blue
            "Purple" -> R.drawable.purple
            "Brown" -> R.drawable.brown
            "Black" -> R.drawable.black
            else -> R.drawable.ic_action_menu // 기본 이미지
        }
    }


    // DatePickerDialog 호출 함수
    private fun showDatePickerDialog(tvDate: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                tvDate.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}