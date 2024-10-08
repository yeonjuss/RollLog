package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.Place
import com.syj2024.project.R
import com.syj2024.project.adapter.OpenMatPlaceAdapter
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.fragment.CalenderFragment
import com.syj2024.project.fragment.LogListFragment
import com.syj2024.project.fragment.OpenMatFragment
import com.syj2024.project.fragment.SiteListFragment
import com.syj2024.project.fragment.PlaceMapFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // 공유할 데이터를 여기에 저장
    var placeList: List<Place>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, CalenderFragment())
            .commit()


        binding.bnv.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.bnv_rc -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CalenderFragment()).commit()
                }

                R.id.bnv_sd -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SiteListFragment()).commit()
                }

                R.id.bnv_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LogListFragment()).commit()
                }

                R.id.bnv_map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PlaceMapFragment()).commit()
                }

                R.id.bnv_openmat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OpenMatFragment()).commit()
                }

            }
            true

        }


        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mypage -> {
                    startActivity(Intent(this, MyPageActivity::class.java))
                    true
                }

                R.id.belt -> {
                    startActivity(Intent(this, BeltStorageActivity::class.java))
                    true
                }

                else -> false

            }
        }


    }



}











