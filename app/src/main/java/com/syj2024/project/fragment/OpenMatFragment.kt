package com.syj2024.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.Place
import com.syj2024.project.R
import com.syj2024.project.adapter.OpenMatPlaceAdapter

class OpenMatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeAdapter: OpenMatPlaceAdapter
    private val placeList = mutableListOf<Place>() // Place 데이터를 저장할 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트 레이아웃을 inflate
        val view = inflater.inflate(R.layout.fragment_open_mat, container, false)

        // RecyclerView 초기화
        recyclerView = view.findViewById(R.id.placeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        // 전달받은 placeList를 arguments에서 가져옴
        arguments?.let {
            val places = it.getParcelableArrayList<Place>("placeList")
            if (places != null) {
                placeList.addAll(places)
            }
        }

        // 어댑터 초기화
        // 어댑터 설정
        placeAdapter = OpenMatPlaceAdapter(placeList)
        recyclerView.adapter = OpenMatPlaceAdapter(placeList)

        return view
    }



}
