package com.syj2024.project.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.R
import com.syj2024.project.adapter.BeltStorageAdapter
import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding
import com.syj2024.project.databinding.ToolbarBeltBinding
import com.syj2024.project.fragment.Item2
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class BeltStorageActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var beltAdapter: BeltStorageAdapter
    val beltList: MutableList<BeltStorageItme> = mutableListOf()
    private lateinit var binding: ToolbarBeltBinding
    private var currentGrade = 0 // 초기 그랄 수
    private lateinit var selectedBeltColor: String

    // 벨트 색상별로 currentGrade를 관리하기 위한 맵
    private val gradeMap = mutableMapOf<String, Int>()

    private val beltColors by lazy {
        resources.getStringArray(R.array.belt_colors).toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ToolbarBeltBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView 설정

        beltAdapter = BeltStorageAdapter(beltList, this)
        binding.recyclerView1.adapter = beltAdapter
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)

        // Spinner 설정
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, beltColors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.beltColorSpinner.adapter = adapter



        // Spinner에서 선택한 벨트 색상에 따른 변경 처리
        binding.beltColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBeltColor = beltColors[position]


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 벨트 추가 버튼 설정
        binding.addBeltButton.setOnClickListener {
            addBelt()
        }
    }

    private fun addBelt() {
        // 새로운 벨트 추가 시 그 벨트는 0그랄부터 시작
        val currentGrade = 0
        gradeMap[selectedBeltColor] = currentGrade


        // 새로운 벨트 아이템 생성
        val newBelt = BeltStorageItme(selectedBeltColor, currentGrade, "date")

        // 벨트 목록에 추가
        beltList.add(newBelt)
        beltAdapter.notifyItemInserted(beltList.size - 1) // 어댑터에 새로운 벨트 추가 알림

        // 새로운 벨트의 그랄을 다시 0부터 4까지 추가할 수 있도록 초기화
        beltAdapter.resetGradesForNewBelt(newBelt)
    }

}





















