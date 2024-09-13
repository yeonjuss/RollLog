package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.syj2024.project.R
import com.syj2024.project.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSign.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))

        }

    }
}