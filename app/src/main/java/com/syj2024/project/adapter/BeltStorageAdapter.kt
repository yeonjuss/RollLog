package com.syj2024.project.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.syj2024.project.R
import com.syj2024.project.activity.BeltStorageItme

import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding
import java.util.Calendar

class BeltStorageAdapter (val beltList: MutableList<BeltStorageItme>,val context: Context ) : Adapter<BeltStorageAdapter.ViewHolder> () {

    inner class ViewHolder(var binding: RecyclerItemListBeltstoreBinding) :
        RecyclerView.ViewHolder(binding.root)
    private var currentGrade = 0



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeltStorageAdapter.ViewHolder {

        val binding =
            RecyclerItemListBeltstoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return beltList.size
    }


    override fun onBindViewHolder(holder: BeltStorageAdapter.ViewHolder, position: Int) {

        val belt = beltList[position]
        holder.binding.tvGrau.text = "${belt.grau} grau"
        holder.binding.tvDate.text = belt.date
        // 벨트 색상에 따라 이미지 설정 (여기서 이미지를 변경할 수 있음)
        holder.binding.beltImage.setImageResource(getBeltImageResource(belt.color))

        // 날짜 클릭 리스너 추가
        holder.binding.ivGrauCalender.setOnClickListener {
            showDatePickerDialog(holder.binding.tvDate)
            // DatePickerDialog 코드
        }






        fun addGradeLayout(grade: Int) {
            // 새로운 레이아웃 생성
            val gradeLayout = LayoutInflater.from(context).inflate(R.layout.grade_item, null)

            // 각 뷰에 데이터 설정
            val tvGrau = gradeLayout.findViewById<TextView>(R.id.tv_grau)
            val tvDate = gradeLayout.findViewById<TextView>(R.id.tv_date)
            val ivGrauCalendar = gradeLayout.findViewById<ImageView>(R.id.iv_grau_calender)

            // 그랄 수와 날짜 설정
            tvGrau.text = "$grade grau"
            tvDate.text = "date" // 여기에 날짜를 입력할 수 있습니다.

            // 날짜 이미지 설정
            ivGrauCalendar.setImageResource(R.drawable.ic_action_calender2) // 적절한 이미지 설정

            // 부모 LinearLayout에 추가
            holder.binding.gradesContainer.addView(gradeLayout)

        }

        // 그랄 추가 버튼 클릭 리스너
        holder.binding.addGradeButton.setOnClickListener {
            if (currentGrade < 4) { // 최대 4그랄까지 가능
                currentGrade++
                addGradeLayout(currentGrade)
            } else {
                Toast.makeText(context, "추가완료.", Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.ivGrauCalender.setOnClickListener {
            showDatePickerDialog(holder.binding.tvDate)
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

    private fun showDatePickerDialog(tvDate: TextView) {
        // 현재 날짜 가져오기
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog 생성
        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            // 선택한 날짜를 TextView에 설정
            tvDate.text = "$selectedYear/${selectedMonth + 1}/$selectedDay"
        }, year, month, day)

        datePickerDialog.show()
    }


}


