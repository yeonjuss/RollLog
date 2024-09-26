package com.syj2024.project.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.R
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.fragment.CalenderFragment
import com.syj2024.project.fragment.LogListFragment
import com.syj2024.project.fragment.SiteListFragment
import com.syj2024.project.fragment.PlaceMapFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, CalenderFragment()).commit()


        binding.bnv.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.bnv_log -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CalenderFragment()).commit()

                R.id.bnv_sd -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SiteListFragment()).commit()

                R.id.bnv_list -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LogListFragment()).commit()

                R.id.bnv_map -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PlaceMapFragment()).commit()

            }
            true
        }

    }// onCreate

} //MainActivity //






