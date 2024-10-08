package com.syj2024.project.fragment

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.syj2024.project.Place
import com.syj2024.project.R
import com.syj2024.project.ResultSearchKeyWord
import com.syj2024.project.RetrofitService
import com.syj2024.project.activity.MainActivity
import com.syj2024.project.databinding.FragmentPlaceMapBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceMapFragment : Fragment() , OnMapReadyCallback {
    lateinit var binding: FragmentPlaceMapBinding
    private lateinit var nMap: NaverMap
    private val placeList = mutableListOf<Place>() // 검색된 장소 정보를 저장하는 리스트

    // 페이지 추적용 변수
    private val maxPages = 10 // 최대 3페이지까지만 요청
    private var currentPage = 1 // 현재 요청 중인 페이지
//    var searchPlaceResponse: ResultSearchKeyWord?=null


    var mylocation: Location? = null


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

        mapFragment.getMapAsync(this)

    }// onViewCreated

       override fun onMapReady(naverMap: NaverMap) {
            this.nMap = naverMap


            searchQuery("주짓수",currentPage)
            searchQuery("와이어 주짓수",currentPage)
            searchQuery("존플랭크 주짓수",currentPage)
            searchQuery("어반 주짓수",currentPage)


       }




    private fun searchQuery(keyword : String , page: Int) {

        // 만약 현재 페이지가 최대 페이지를 넘으면 요청을 중단
        if (page > maxPages) {
            Log.d("PlaceMapFragment", "최대 페이지인 $maxPages 페이지에 도달했습니다.")
            return
        }


        val retrofit = Retrofit.Builder() // Retrofit 구현
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RetrofitService::class.java)
        val call = api.SearchPlace(API_KEY, keyword, page)

        call.enqueue(object : Callback<ResultSearchKeyWord> {
            override fun onResponse(
                call: Call<ResultSearchKeyWord>,
                response: Response<ResultSearchKeyWord>
            ) {
//                // json을 파싱한 결과 받기 - 그 결과를 Fragment들에서 사용하기에 멤버변수로 참조하기
//                searchPlaceResponse=response.body()

                Log.d("PlaceMapFragment", "Raw : ${response.raw()}")
                Log.d("PlaceMapFragment", "Body : ${response.body()}")

                if (response.isSuccessful  && response.body() != null) {
                    val result = response.body()


//                    if (result != null) {
//                        Log.d("PlaceMapFragment", "Result: ${result.meta}")

                        val places = response.body()?.documents?.map { document ->
                            Place(
                                id = document.id,
                                place_name = document.place_name,
                                road_address_name = document.road_address_name,
                                x = document.x, // 경도
                                y = document.y,  // 위도
                                place_url = document.place_url

                            )


                        } ?: emptyList()


                    // 리스트에 저장된 장소 정보를 placeList에 추가
                    placeList.addAll(places)

                    // MainActivity에 데이터를 저장
                    val mainActivity = activity as? MainActivity
                    mainActivity?.placeList = placeList

                    // UI 스레드에서 마커 추가
                    requireActivity().runOnUiThread {
                        places.forEach { place ->
                            addMarker(place)
                        }
                    }

                    if (result?.meta?.is_end == false && page < maxPages) {
                        currentPage++ // 다음 페이지로 넘어가기 위해 currentPage 증가
                        Log.d("PlaceMapFragment", "Fetching next page: $currentPage")
                        searchQuery(keyword, currentPage)  // 다음 페이지를 요청
                    }


                    // placeList의 사이즈 확인 (디버깅용 로그 추가)
                    Log.d("PlaceMapFragment", "Place list size: ${placeList.size}")

                } else {
                    Log.w("PlaceMapFragment", "검색 결과 실패: ${response.errorBody()}")
                }

            }

            override fun onFailure(call: Call<ResultSearchKeyWord>, t: Throwable) {
                Log.w("PlaceMapFragment", "통신 실패 : ${t.message}")
            }
        })


    } // searchQuery


    // OpenMatFragment로 데이터 전달
//    private fun openPlaceListFragment(placeList: List<Place>) {
//        val fragment = OpenMatFragment()
//
//        // Bundle을 사용하여 데이터를 전달
//        val bundle = Bundle().apply {
//            putParcelableArrayList("placeList", ArrayList(placeList)) // Parcelable로 전달
//        }
//        fragment.arguments = bundle
//
//        // Fragment 전환
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }



    private fun addMarker(place: Place) {
        val marker = Marker()
        marker.position = LatLng(place.y.toDouble(),place.x.toDouble()) // 위도 경도
        marker.icon = OverlayImage.fromResource(R.drawable.ic_action_location)
        marker.captionText = place.place_name
        marker.map = nMap



    }


}









