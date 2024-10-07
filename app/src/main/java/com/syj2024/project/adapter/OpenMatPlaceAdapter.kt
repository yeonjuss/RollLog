package com.syj2024.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.Place
import com.syj2024.project.R

class OpenMatPlaceAdapter(private val placeList: List<Place>) : RecyclerView.Adapter<OpenMatPlaceAdapter.PlaceViewHolder>() {

    // ViewHolder 클래스 정의
    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeNameTextView: TextView = itemView.findViewById(R.id.tvPlaceName)
        val placeCoordsTextView: TextView = itemView.findViewById(R.id.tvPlaceCoords)
    }

    // 뷰홀더 생성 시 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_place, parent, false)
        return PlaceViewHolder(view)
    }

    // 데이터와 뷰를 바인딩
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeNameTextView.text = place.place_name
        holder.placeCoordsTextView.text = "Lat: ${place.y}, Lng: ${place.x}"
    }

    // 리스트의 아이템 개수 반환
    override fun getItemCount(): Int {
        return placeList.size
    }
}