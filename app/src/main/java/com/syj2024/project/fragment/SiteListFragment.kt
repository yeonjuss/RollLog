package com.syj2024.project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.R
import com.syj2024.project.adapter.SiteListAdapter
import com.syj2024.project.databinding.FragmentSiteListBinding

class SiteListFragment : Fragment() {

    val recyclerView: RecyclerView by lazy { requireView().findViewById(R.id.recycler_view) }
    var itemList: MutableList<Item> = mutableListOf()
    lateinit var binding:FragmentSiteListBinding

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
            binding= FragmentSiteListBinding.inflate(inflater,container,false)
            return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemList.add(Item("스트릿 주짓수",R.drawable.ic_action_arrow ))
        itemList.add(Item("예거 주짓수",R.drawable.ic_action_arrow ))
        itemList.add(Item("SGAA 주짓수",R.drawable.ic_action_arrow ))
        itemList.add(Item("주짓수코리아 ",R.drawable.ic_action_arrow ))
        itemList.add(Item("퀸즈 컴뱃",R.drawable.ic_action_arrow ))
        itemList.add(Item("퀸즈 컴뱃",R.drawable.ic_action_arrow ))
        itemList.add(Item("퀸즈 컴뱃",R.drawable.ic_action_arrow ))
        itemList.add(Item("퀸즈 컴뱃",R.drawable.ic_action_arrow ))


        recyclerView.adapter = SiteListAdapter(requireContext(), itemList)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

}
