package com.syj2024.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaceViewModel: ViewModel() {

    // LiveData로 데이터를 저장하여 다른 프래그먼트에서 관찰할 수 있게 함
    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> get() = _placeList

    // 데이터 설정 함수
    fun setPlaceList(places: List<Place>) {
        _placeList.value = places
    }
}
