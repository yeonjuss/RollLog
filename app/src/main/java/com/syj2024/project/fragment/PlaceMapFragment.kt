package com.syj2024.project.fragment

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.syj2024.project.Place
import com.syj2024.project.PlaceViewModel
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

class PlaceMapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK a7a4255f2f8f7c8a0cf783026911cd5a" // REST API 키
    }

    lateinit var binding: FragmentPlaceMapBinding
    private lateinit var nMap: NaverMap
    private val placeList = mutableListOf<Place>() // 검색된 장소 정보를 저장하는 리스트

    // ViewModel 초기화
    private lateinit var viewModel: PlaceViewModel
    private var isMapReady = false // 지도 준비 여부를 확인하기 위한 변수


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.setActionBarTitle("근처체육관")
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {


                    childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                }

        // 지도 비동기 로드
        mapFragment.getMapAsync(this)



        // ViewModel 초기화 (MainActivity와 공유)
        viewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)




        // ViewModel의 데이터를 관찰하고, 마커를 추가
        viewModel.placeList.observe(viewLifecycleOwner, { places ->
            // 지도 준비 상태에서만 마커를 추가
            if (isMapReady) {
                addMarkersToMap(places)
            } else {
                // 지도 준비 전에 데이터를 로드한 경우, 지도 준비 후 추가할 수 있도록 대기
                placeList.addAll(places)
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d("PlaceMapFragment", "onMapReady called: NaverMap is ready")
        this.nMap = naverMap
        isMapReady = true // 지도 준비 완료


        // 카메라 초기 위치를 서울로 설정 (예시)
        val initialPosition = LatLng(37.5665, 126.9780) // 서울 중심 좌표
        val cameraUpdate = CameraUpdate.scrollTo(initialPosition)
        naverMap.moveCamera(cameraUpdate)



        // ViewModel에서 placeList를 가져와 마커 추가
        val places = viewModel.placeList.value ?: emptyList()
        if (places.isNotEmpty()) {
            Log.d("Marker", "onMapReady - 마커 추가: ${places.size}개의 장소")
            addMarkersToMap(places)
        } else {
            Log.d("Marker", "onMapReady - 아직 로드된 장소가 없습니다.")
        }
    }




    // 마커를 여러 개 추가하는 함수
    private fun addMarkersToMap(places: List<Place>) {
        places.forEach { place ->
            addMarker(place)
        }
    }


    private fun addMarker(place: Place) {
        val marker = Marker()
        marker.position = LatLng(place.y.toDouble(),place.x.toDouble()) // 위도 경도
        marker.icon = OverlayImage.fromResource(R.drawable.ic_action_location)
        marker.captionText = place.place_name
        marker.map = nMap



    }


}









