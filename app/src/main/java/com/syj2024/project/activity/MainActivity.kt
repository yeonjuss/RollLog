package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.syj2024.project.Place
import com.syj2024.project.PlaceViewModel
import com.syj2024.project.R
import com.syj2024.project.ResultSearchKeyWord
import com.syj2024.project.RetrofitService
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.databinding.CustomActionbarTitleBinding
import com.syj2024.project.fragment.CalenderFragment
import com.syj2024.project.fragment.LogListFragment
import com.syj2024.project.fragment.OpenMatFragment
import com.syj2024.project.fragment.SiteListFragment
import com.syj2024.project.fragment.PlaceMapFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val maxPages = 10 // 최대 10페이지까지만 요청
    private var currentPage = 1 // 현재 요청 중인 페이지
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: PlaceViewModel // ViewModel 초기화


    // 커스텀 액션바 레이아웃 바인딩
    private lateinit var actionBarBinding: CustomActionbarTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        // 툴바 설정
        setSupportActionBar(binding.toolbar)


        // 커스텀 타이틀 바인딩 설정
        actionBarBinding = CustomActionbarTitleBinding.inflate(layoutInflater)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false) // 기본 타이틀 비활성화
            customView = actionBarBinding.root // 커스텀 액션바 뷰 설정
            setDisplayShowCustomEnabled(true) // 커스텀 타이틀 활성화
        }


        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        // 앱이 시작되면 데이터를 로드하고 ViewModel에 저장
        loadPlaceData() // 앱 시작 시 데이터 로드

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





        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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


    } //onCreate

    // 메뉴 인플레이트 (툴바에 메뉴 표시)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)  // 메뉴 리소스 파일을 툴바에 인플레이트
        return true
    }



    // 액션바 타이틀 설정
    fun setActionBarTitle(title: String) {
        actionBarBinding.customTitle.text = title
    }


    // 데이터를 불러오는 메서드
    private fun loadPlaceData() {
        searchQuery("주짓수",currentPage)
        searchQuery("와이어 주짓수",currentPage)
        searchQuery("존플랭크 주짓수",currentPage)
        searchQuery("어반 주짓수",currentPage)
    }

    // Retrofit API를 통해 데이터를 불러오는 메서드
    private fun searchQuery(keyword: String,page: Int) {

        // 만약 현재 페이지가 최대 페이지를 넘으면 요청을 중단
        if (page > maxPages) {
            Log.d("PlaceMapFragment", "최대 페이지인 $maxPages 페이지에 도달했습니다.")
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(PlaceMapFragment.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RetrofitService::class.java)
        val call = api.SearchPlace(PlaceMapFragment.API_KEY, keyword, page)

        call.enqueue(object : Callback<ResultSearchKeyWord> {
            override fun onResponse(call: Call<ResultSearchKeyWord>, response: Response<ResultSearchKeyWord>) {
                if (response.isSuccessful  && response.body() != null) {
                    val result = response.body()

                    val places = response.body()?.documents?.map { document ->
                        Place(
                            id = document.id,
                            place_name = document.place_name,
                            road_address_name = document.road_address_name,
                            x = document.x,
                            y = document.y,
                            place_url = document.place_url
                        )
                    } ?: emptyList()

                    // 페이지가 끝났는지 확인
                    if (result?.meta?.is_end == true) {
//                        Log.d("PlaceMapFragment", "마지막 페이지에 도달함, 더 이상 데이터가 없습니다.")
                    } else if (page < maxPages) {
                        currentPage++ // 다음 페이지로 넘어가기 위해 currentPage 증가
//                        Log.d("PlaceMapFragment", "Fetching next page: $currentPage")
                        searchQuery(keyword, currentPage)  // 다음 페이지를 요청
                    } else {
                        Log.d("PlaceMapFragment", "최대 페이지에 도달: 더 이상 페이지 요청을 하지 않음")
                    }


                    // ViewModel에 데이터를 저장 (기존 데이터와 합쳐서 추가)
                    val currentList = viewModel.placeList.value ?: emptyList()
                    viewModel.setPlaceList(currentList + places)

                }
            }

            override fun onFailure(call: Call<ResultSearchKeyWord>, t: Throwable) {
                Log.e("MainActivity", "API 요청 실패: ${t.message}")
            }
        })
    }


}















