package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.syj2024.project.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // 파이어베이스 초기화
        auth = Firebase.auth

        // 회원가입 버튼 클릭 리스너
        binding.btnSign.setOnClickListener {
            // 입력된 사용자 정보 가져오기
            val name = binding.inputName.editText?.text.toString()
            val email = binding.inputEmail.editText?.text.toString()
            val password = binding.inputPass.editText?.text.toString()

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()

                }  else{

                        Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show()
                    }

                    startActivity(Intent(this, SignUpActivity2::class.java))


        }




        }




    }
}
