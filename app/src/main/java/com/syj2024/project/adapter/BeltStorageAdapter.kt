package com.syj2024.project.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.syj2024.project.R
import com.syj2024.project.activity.BeltStorageItme
import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding
import java.util.Calendar

class BeltStorageAdapter(val beltList: MutableList<BeltStorageItme>, val context: Context, val db: FirebaseFirestore, val userId: String) :
    RecyclerView.Adapter<BeltStorageAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: RecyclerItemListBeltstoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(belt: BeltStorageItme) {
            binding.tvGrau.text = "${belt.grau} grau"
//            binding.tvDate.text = belt.date
            binding.beltImage.setImageResource(getBeltImageResource(belt.color))

            // DatePickerDialog 호출
            binding.ivGrauCalender.setOnClickListener {
                showDatePickerDialog(binding.tvDate) { selectedDate ->
                    binding.tvDate.text = selectedDate
                    belt.date = selectedDate
                }
            }

            resetGradesForNewBelt(belt)
        }

        fun resetGradesForNewBelt(belt: BeltStorageItme) {
            binding.gradesContainer.removeAllViews() // 그랄 레이아웃 초기화
            for (grade in 1..4) { // 0그랄부터 4그랄까지 추가
                addGradeLayout(grade)
            }
        }

        fun addGradeLayout(grade: Int) {
            val gradeLayout = LayoutInflater.from(context).inflate(R.layout.grade_item, null)
            val tvGrau = gradeLayout.findViewById<TextView>(R.id.tv_grau)
            val tvDate = gradeLayout.findViewById<TextView>(R.id.tv_date)
            val ivGrauCalendar = gradeLayout.findViewById<ImageView>(R.id.iv_grau_calender)

            tvGrau.text = "$grade grau"
            tvDate.text = "date"

            binding.gradesContainer.addView(gradeLayout)

            // DatePickerDialog 연결
            ivGrauCalendar.setOnClickListener {
                showDatePickerDialog(tvDate) { selectedDate ->
                    tvDate.text = selectedDate
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
                "grau" to belt.grau,
                "promotionDate" to belt.date
            )
            db.collection("users").document(userId).collection("belts")
                .add(beltData)
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