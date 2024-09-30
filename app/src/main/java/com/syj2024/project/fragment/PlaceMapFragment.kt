package com.syj2024.project.fragment

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import com.syj2024.project.Place
import com.syj2024.project.R
import com.syj2024.project.ResultSearchKeyWord
import com.syj2024.project.RetrofitHelper
import com.syj2024.project.RetrofitService
import com.syj2024.project.databinding.FragmentPlaceMapBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PlaceMapFragment : Fragment() {
    lateinit var binding: FragmentPlaceMapBinding
    private lateinit var nMap: NaverMap
//    var searchQuery: String = "주짓수"

    var mylocation: Location? = null
    var resultSearchKeyWord: ResultSearchKeyWord? = null

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK a7a4255f2f8f7c8a0cf783026911cd5a" // REST API 키
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                }




        searchQuery("주짓수")




    }  // onViewCreated





    private fun searchQuery(keyword : String) {

        val retrofit = Retrofit.Builder() // Retrofit 구현
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(RetrofitService::class.java)
        val call = api.SearchPlace(API_KEY, keyword)

        call.enqueue(object : Callback<ResultSearchKeyWord> {
            override fun onResponse(
                call: Call<ResultSearchKeyWord>,
                response: Response<ResultSearchKeyWord>
            ) {

                Log.d("PlaceMapFragment", "Raw : ${response.raw()}")
                Log.d("PlaceMapFragment", "Body : ${response.body()}")
            }

            override fun onFailure(call: Call<ResultSearchKeyWord>, t: Throwable) {
                Log.w("PlaceMapFragment", "통신 실패 : ${t.message}")
            }
        })


    } // searchQuery

    private fun addMarker(place: Place) {
        val marker = Marker()
        marker.position = LatLng(place.y.toDouble(),place.x.toDouble()) // 위도 경도
        marker.icon = OverlayImage.fromResource(R.drawable.baseline_people_open)
        marker.captionText = place.place_name
        marker.map = nMap



    }


}









