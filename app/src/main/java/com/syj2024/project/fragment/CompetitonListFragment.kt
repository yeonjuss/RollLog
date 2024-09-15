package com.syj2024.project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syj2024.project.R
import com.syj2024.project.databinding.FragmentCompetitonListBinding

class CompetitonListFragment : Fragment() {

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
            binding= FragmentCompetitonListBinding.inflate(inflater,container,false)
            return binding.root
    }

    lateinit var binding:FragmentCompetitonListBinding

}
