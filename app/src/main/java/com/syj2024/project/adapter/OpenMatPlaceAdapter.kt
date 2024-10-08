package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.syj2024.project.Place
import com.syj2024.project.databinding.RecyclerFragmentItemPlaceBinding

class OpenMatPlaceAdapter( private var placeList: List<Place>,val context: Context) : RecyclerView.Adapter<OpenMatPlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(var binding: RecyclerFragmentItemPlaceBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenMatPlaceAdapter.PlaceViewHolder {
        val binding =
            RecyclerFragmentItemPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
        return PlaceViewHolder (binding)
    }

    override fun onBindViewHolder(holder: OpenMatPlaceAdapter.PlaceViewHolder, position: Int) {
        val place = placeList[position]

        holder.binding.cv
        holder.binding.tvPlaceName.text = place.place_name
        holder.binding.tvAddress.text = place.road_address_name
        holder.binding.tvUrl.visibility = View.GONE

        // URL 아이콘 클릭 시 브라우저로 연결
        holder.binding.ivUrl.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(place.place_url) // place의 URL로 브라우저 열기
            }
            context.startActivity(urlIntent) // context를 사용하여 인텐트 실행
        }

    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    // 추가된 updateData 메서드
    fun updateData(newPlaces: List<Place>) {
        this.placeList = newPlaces
        notifyDataSetChanged() // 데이터가 변경되었음을 RecyclerView에 알림
    }


}


