package com.syj2024.project.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.databinding.ToolbarMypageBinding

class MyPageActivity : AppCompatActivity() {
    private val binding by lazy { ToolbarMypageBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}