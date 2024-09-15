package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.databinding.ActivitySignup2Binding
import com.syj2024.project.databinding.ActivitySignupBinding

class SignUpActivity2 : AppCompatActivity() {

    val binding by lazy { ActivitySignup2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSign2.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}