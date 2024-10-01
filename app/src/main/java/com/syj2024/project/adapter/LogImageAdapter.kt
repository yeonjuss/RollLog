package com.syj2024.project.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.syj2024.project.R
import com.syj2024.project.databinding.RecyclerItemListFragmentBinding
import com.syj2024.project.databinding.RecyclerviewLogimageActivityBinding


class LogImageAdapter (val context: Context, val photoList:MutableList<Uri?>) : Adapter<LogImageAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_log)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater=LayoutInflater.from(context)
        val view= inflater.inflate(R.layout.recyclerview_logimage_activity, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Glide.with(context).load(photoList[position]).into(holder.imageView)


    } //viewHolder

    override fun getItemCount(): Int {
        return photoList.size
    }




    }

