package com.syj2024.project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syj2024.project.databinding.FragmentLogListBinding


class LogListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLogListBinding.inflate(inflater,container,false)
        return binding.root
    }

    lateinit var binding: FragmentLogListBinding





}
