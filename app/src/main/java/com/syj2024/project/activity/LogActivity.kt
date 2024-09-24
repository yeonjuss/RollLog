package com.syj2024.project.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.databinding.ActivityLogBinding


class LogActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLogBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectedDate = intent.getStringExtra("selectedDate")
        binding.selectedDateTv.text = "$selectedDate"


    }
    
}

