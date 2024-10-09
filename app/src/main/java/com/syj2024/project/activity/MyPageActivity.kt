package com.syj2024.project.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.databinding.ToolbarMypageBinding

class MyPageActivity : AppCompatActivity() {
    private val binding by lazy { ToolbarMypageBinding.inflate(layoutInflater) }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Firebase 초기화
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        // 현재 로그인된 사용자 정보 가져오기
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Firestore에서 사용자 정보 불러오기
            loadUserData(userId)
        } else {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // Firestore에서 사용자 데이터 불러오기
    private fun loadUserData(userId: String) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Firestore 문서에서 데이터 가져오기
                    val name = document.getString("name")
                    val email = document.getString("email")
                    val selectedBeltName = document.getString("selectedBeltName")
                    val selectedBeltStory = document.getString("selectedBeltStory")
                    val selectedBeltImageRes = document.getLong("selectedBeltImage")?.toInt()

                    // 가져온 데이터를 UI에 표시
                    binding.userNameTextView.text = name
                    binding.userEmailTextView.text = email
                    binding.selectedBeltTextView.text = selectedBeltName
                    binding.beltStoryTextView.text = selectedBeltStory  + "grau"
                    selectedBeltImageRes?.let { binding.selectedBeltImageView.setImageResource(it) }
                } else {
                    Toast.makeText(this, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "데이터 불러오기 실패: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}
