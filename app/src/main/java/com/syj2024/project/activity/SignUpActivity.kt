package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syj2024.project.R
import com.syj2024.project.databinding.ActivitySignupBinding
import com.syj2024.project.databinding.BeltBottomSheetBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var selectedBeltName: String = ""
    private var selectedBeltStory: String = ""
    private var selectedBeltImage: Int = 0 // 이미지 리소스 ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 파이어베이스 초기화
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance() // Firestore 초기화


        // 회원가입 버튼 클릭 리스너
        binding.btnSign.setOnClickListener {
            // 입력된 사용자 정보 가져오기
            val name = binding.inputName.editText?.text.toString()
            val email = binding.inputEmail.editText?.text.toString()
            val password = binding.inputPass.editText?.text.toString()

            var isValid = true


            if (name.isEmpty()) {
                binding.inputName.error = "이름을 입력해주세요"
                isValid = false
            } else {
                binding.inputName.error = null  // 오류 메시지를 없앰
            }

            if (email.isEmpty()) {
                binding.inputEmail.error = "이메일을 입력해주세요"
                isValid = false
            } else {
                binding.inputEmail.error = null  // 오류 메시지를 없앰
            }

            if (password.isEmpty()) {
                binding.inputPass.error = "비밀번호를 입력해주세요"
                isValid = false
            } else {
                binding.inputPass.error = null  // 오류 메시지를 없앰
            }

            if (isValid) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 회원가입 성공, Firestore에 사용자 정보 및 선택한 벨트 정보 저장
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                saveUserDataToFirestore(userId,name, email)
                            }

                            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    .addOnFailureListener { exception ->
                        // 실패 이유를 로그로 출력
                        Toast.makeText(this, "회원가입 실패: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                        Log.e("SignUpError", "회원가입 실패 이유: ", exception)
                    }
            }
        }



        // 버튼 클릭 시 BottomSheetDialog 호출
        binding.showBottomSheetButton.setOnClickListener {
            showBeltBottomSheet()
        }

    } // onCreateView

    // Firestore에 사용자 데이터와 선택한 벨트 정보 저장
    private fun saveUserDataToFirestore(userId: String, name: String, email: String) {
        val beltData = hashMapOf(
            "name" to name,
            "email" to email,
            "selectedBeltName" to selectedBeltName,
            "selectedBeltStory" to selectedBeltStory,
            "selectedBeltImage" to selectedBeltImage // 리소스 ID 저장
        )

        db.collection("users").document(userId)
            .set(beltData)
            .addOnSuccessListener {
                Toast.makeText(this, "사용자 정보 저장 완료", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "사용자 정보 저장 실패", Toast.LENGTH_SHORT).show()
            }
    }







    private fun showBeltBottomSheet() {
        // BottomSheetDialog 생성
        val bottomSheetDialog = BottomSheetDialog(this)

        // BeltBottomSheetBinding을 사용해 BottomSheet 레이아웃을 바인딩
        val bottomSheetBinding = BeltBottomSheetBinding.inflate(LayoutInflater.from(this))

        // BottomSheet에 레이아웃 설정
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // 각각의 벨트 선택 시 동작 설정 (예: white belt 선택 시)
        bottomSheetBinding.beltImageView.setOnClickListener {

            selectedBeltName = "White Belt"
            selectedBeltStory = bottomSheetBinding.beltgrau.text.toString()
            selectedBeltImage = R.drawable.white // 이미지 리소스 저장
            updateBeltSelection()
            bottomSheetDialog.dismiss()

        }

        bottomSheetBinding.beltImageView2.setOnClickListener {

            selectedBeltName = "Blue Belt"
            selectedBeltStory = bottomSheetBinding.beltgrau2.text.toString()
            selectedBeltImage = R.drawable.blue // 이미지 리소스 저장
            updateBeltSelection()
            bottomSheetDialog.dismiss()

        }

        bottomSheetBinding.beltImageView3.setOnClickListener {

            selectedBeltName = "Purple Belt"
            selectedBeltStory = bottomSheetBinding.beltgrau3.text.toString()
            selectedBeltImage = R.drawable.purple // 이미지 리소스 저장
            updateBeltSelection()
            bottomSheetDialog.dismiss()

        }

        bottomSheetBinding.beltImageView4.setOnClickListener {

            selectedBeltName = "Brown Belt"
            selectedBeltStory = bottomSheetBinding.beltgrau4.text.toString()
            selectedBeltImage = R.drawable.brown // 이미지 리소스 저장
            updateBeltSelection()
            bottomSheetDialog.dismiss()

        }

        bottomSheetBinding.beltImageView5.setOnClickListener {

            selectedBeltName = "Black Belt"
            selectedBeltStory = bottomSheetBinding.beltgrau5.text.toString()
            selectedBeltImage = R.drawable.black // 이미지 리소스 저장
            updateBeltSelection()
            bottomSheetDialog.dismiss()

        }


        // BottomSheetDialog 표시
        bottomSheetDialog.show()
    }


    // 선택된 벨트 정보를 UI에 업데이트
    private fun updateBeltSelection() {
        binding.selectedBeltTextView.text = selectedBeltName
        binding.inputgrauTextView.text = selectedBeltStory
        binding.selectedBeltImageView.setImageResource(selectedBeltImage)
    }


}
