package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.syj2024.project.databinding.RecyclerItemListLogfragmentBinding

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
        Glide.with(context).load(site.img).into(holder.binding.ivSite)



    }

    override fun getItemCount(): Int {
        return  siteList.size




    }


}

