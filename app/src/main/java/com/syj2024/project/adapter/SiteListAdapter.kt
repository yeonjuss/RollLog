package com.syj2024.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.syj2024.project.R
import com.syj2024.project.databinding.RecyclerItemListFragmentBinding
import com.syj2024.project.fragment.Item


class SiteListAdapter constructor(val context:Context, val siteList: List<Item>) : Adapter<SiteListAdapter.VH>() {

    inner class VH constructor(itemView: View) : ViewHolder(itemView) {
        val iv_site: ImageView by lazy { itemView.findViewById(R.id.iv_site) }
        val tv_address: TextView by lazy { itemView.findViewById(R.id.tv_address) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView: View = inflater.inflate(R.layout.recycler_item_list_fragment, parent, false)
        return VH(itemView)
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val site = siteList.get(position)
        holder.tv_address.text=site.address
        Glide.with(context).load(site.imageResId).into(holder.iv_site)
    }
}

