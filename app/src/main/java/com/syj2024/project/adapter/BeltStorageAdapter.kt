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
import androidx.compose.ui.window.application
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syj2024.project.R
import com.syj2024.project.activity.BeltStorageItme

import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding

import java.util.Calendar


class BeltStorageAdapter(val beltList: MutableList<BeltStorageItme>,
                         val context: Context,
                         val db: FirebaseFirestore,
                         val userId: String) :
    RecyclerView.Adapter<BeltStorageAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: RecyclerItemListBeltstoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(belt: BeltStorageItme) {
            binding.beltImage.setImageResource(getBeltImageResource(belt.color))
            if (binding.gradesContainer.childCount > 0) {
                binding.gradesContainer.removeAllViews() // 중복 방지: 이미 추가된 뷰는 모두 제거
            }

            // 각 그랄별로 날짜 설정
            for (grade in 0..4) {
                val savedDate = belt.dates.getOrNull(grade) ?: ""
                addGradeLayout(grade, savedDate, belt)
            }

//            resetGradesForNewBelt(belt)
        }


        // 각 그랄의 레이아웃 추가 함수
        fun addGradeLayout(grade: Int, savedDate: String, belt: BeltStorageItme) {
            val gradeLayout = LayoutInflater.from(context).inflate(R.layout.grade_item, null)
            val tvGrau = gradeLayout.findViewById<TextView>(R.id.tv_grau)
            val tvDate = gradeLayout.findViewById<TextView>(R.id.tv_date)
            val ivGrauCalendar = gradeLayout.findViewById<ImageView>(R.id.iv_grau_calender)

            tvGrau.text = "$grade grau"
            tvDate.text = if (savedDate.isNotEmpty()) savedDate else "date"

            binding.gradesContainer.addView(gradeLayout)

            // DatePickerDialog 연결
            ivGrauCalendar.setOnClickListener {
                showDatePickerDialog(tvDate) { selectedDate ->
                    tvDate.text = selectedDate
                    belt.dates[grade] = selectedDate // 선택된 날짜 그랄에 저장
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerItemListBeltstoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = beltList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(beltList[position])
    }

    fun saveAllBeltsToFirestore() {
        for (belt in beltList) {
            val beltData = hashMapOf(
                "beltName" to belt.color,
                "promotionDate" to belt.dates
            )
            db.collection("users").document(userId).collection("belts")
                .document(belt.color) // 문서 ID를 띠 색깔로 사용
                .set(beltData) // set을 사용하여 기존 문서를 덮어씌움
                .addOnSuccessListener {
                    Toast.makeText(context, "벨트 정보 저장 완료", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "저장 실패: $e", Toast.LENGTH_SHORT).show()
                }
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

    private fun showDatePickerDialog(tvDate: TextView, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                onDateSelected(selectedDate) // 선택된 날짜 전달
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}