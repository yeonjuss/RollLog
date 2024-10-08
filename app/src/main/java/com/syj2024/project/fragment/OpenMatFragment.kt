package com.syj2024.project.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.Place
import com.syj2024.project.R
import com.syj2024.project.activity.MainActivity
import com.syj2024.project.adapter.OpenMatPlaceAdapter
import com.syj2024.project.databinding.FragmentOpenMatBinding
import com.syj2024.project.databinding.RecyclerFragmentItemPlaceBinding
import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding

class OpenMatFragment : Fragment() {
    inner class ViewHolder(var binding: RecyclerFragmentItemPlaceBinding,var context: Context) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var binding: FragmentOpenMatBinding
    private lateinit var placeAdapter: OpenMatPlaceAdapter
    private lateinit var placeList: List<Place> // Place 데이터를 저장할 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenMatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MainActivity에서 데이터를 가져옴
        val mainActivity = activity as? MainActivity
        placeList = mainActivity?.placeList ?: emptyList()

        // RecyclerView 설정
        binding.placeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // RecyclerView 어댑터 설정
        placeAdapter = OpenMatPlaceAdapter(placeList, requireContext()) // context를 추가로 전달
        binding.placeRecyclerView.adapter = placeAdapter
    }
}