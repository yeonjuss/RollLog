package com.syj2024.project.activity


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syj2024.project.R
import com.syj2024.project.adapter.BeltStorageAdapter
import com.syj2024.project.databinding.ToolbarBeltBinding




class BeltStorageActivity: AppCompatActivity() {

    private lateinit var beltAdapter: BeltStorageAdapter
    val beltList: MutableList<BeltStorageItme> = mutableListOf()
    private lateinit var binding: ToolbarBeltBinding
    private lateinit var selectedBeltColor: String

    // Firestore 인스턴스
    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String

    private val beltColors by lazy {
        resources.getStringArray(R.array.belt_colors).toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ToolbarBeltBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firestore 인스턴스 초기화
        db = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // RecyclerView 설정
        beltAdapter = BeltStorageAdapter(beltList, this, db, userId)
        binding.recyclerView1.adapter = beltAdapter
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)

        // Firestore에서 데이터를 불러와 RecyclerView에 표시
        loadBeltsFromFirestore()






        // Spinner 설정
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, beltColors)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.beltColorSpinner.adapter = spinnerAdapter



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

        // 저장 버튼 클릭 리스너 추가: Firestore에 벨트 정보를 저장
        binding.saveBtn.setOnClickListener {
            beltAdapter.saveAllBeltsToFirestore()  // 어댑터를 통해 Firestore로 벨트 정보 저장
        }
    }




    // Firestore에서 저장된 데이터를 불러와 RecyclerView에 표시하는 함수
    private fun loadBeltsFromFirestore() {
        db.collection("users").document(userId).collection("belts")
            .get()
            .addOnSuccessListener { result ->
                beltList.clear() // 기존 목록 초기화
                for (document in result) {
                    val beltName = document.getString("beltName") ?: ""
                    val grau = document.getLong("grau")?.toInt() ?: 0
                    val promotionDate = document.getString("promotionDate") ?: ""
                    val belt = BeltStorageItme(beltName, grau, promotionDate)

                    beltList.add(belt) // 목록에 추가
                }
                beltAdapter.notifyDataSetChanged() // RecyclerView 업데이트
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "데이터 불러오기 실패: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBelt() {
        val currentGrade = 0
        val newBelt = BeltStorageItme(selectedBeltColor, currentGrade, "날짜를 선택하세요")
        beltList.add(newBelt)
        beltAdapter.notifyItemInserted(beltList.size - 1)
    }
}
