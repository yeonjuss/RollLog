package com.syj2024.project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.Place
import com.syj2024.project.PlaceViewModel
import com.syj2024.project.R
import com.syj2024.project.activity.MainActivity
import com.syj2024.project.adapter.OpenMatPlaceAdapter
import com.syj2024.project.databinding.FragmentOpenMatBinding
import com.syj2024.project.databinding.RecyclerFragmentItemPlaceBinding
import com.syj2024.project.databinding.RecyclerItemListBeltstoreBinding

class OpenMatFragment : Fragment() {
    inner class ViewHolder(var binding: RecyclerFragmentItemPlaceBinding,var context: Context) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var viewModel: PlaceViewModel
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

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.setActionBarTitle("오픈매트")
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

//        // MainActivity에서 데이터를 가져옴
//        val mainActivity = activity as? MainActivity
//        placeList = mainActivity?.placeList ?: emptyList()

        // RecyclerView 초기화
        placeAdapter = OpenMatPlaceAdapter(emptyList(), requireContext()) // 초기에는 빈 리스트 전달
        binding.placeRecyclerView.adapter = placeAdapter
        binding.placeRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        // ViewModel의 placeList를 관찰하고, 업데이트가 있을 때 어댑터에 적용
        viewModel.placeList.observe(viewLifecycleOwner, { places ->
            Log.d("OpenMatFragment", "업데이트된 데이터 받음: ${places.size} 개")
            placeAdapter.updateData(places)
        })

    }
}