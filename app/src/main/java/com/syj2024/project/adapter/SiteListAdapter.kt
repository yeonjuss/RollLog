package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.syj2024.project.databinding.RecyclerItemListFragmentBinding
import com.syj2024.project.fragment.Item


class SiteListAdapter constructor(val context:Context, val siteList: List<Item>) : Adapter<SiteListAdapter.VH>() {


    inner class VH(var binding:RecyclerItemListFragmentBinding) : ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteListAdapter.VH {
        val binding= RecyclerItemListFragmentBinding.inflate(LayoutInflater.from(context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: SiteListAdapter.VH, position: Int) {

        val site = siteList.get(position)

        holder.binding.tvAddress.text = site.address
//      Glide.with(context).load(site.img).into(holder.binding.ivSite)


        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(site.url) // 아이템 클릭 시 URL 열기
            context.startActivity(intent)


        }



    }

    override fun getItemCount(): Int {
        return  siteList.size




    }


}

