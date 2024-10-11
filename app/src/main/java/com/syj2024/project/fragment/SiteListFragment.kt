package com.syj2024.project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.R
import com.syj2024.project.activity.MainActivity
import com.syj2024.project.adapter.SiteListAdapter
import com.syj2024.project.databinding.FragmentSiteListBinding

class SiteListFragment : Fragment() {

    var itemList: MutableList<Item> = mutableListOf()
    lateinit var binding:FragmentSiteListBinding

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
            binding= FragmentSiteListBinding.inflate(inflater,container,false)
            return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.setActionBarTitle("대회일정")
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        itemList.add(Item(R.drawable.street,"스트릿 주짓수","https://www.street-jiujitsu.com/"  ))
        itemList.add(Item(R.drawable.jagger,"예거스컵 주짓수","https://www.jagerscup.com/" ))
        itemList.add(Item(R.drawable.sgaa,"SGAA 주짓수","https://jiujitsukor.com/board/gallery/list.html?board_no=8" ))
        itemList.add(Item(R.drawable.img_1,"퀸즈 컴뱃","https://www.instagram.com/queenz_combat/" ))
        itemList.add(Item(R.drawable.narsha,"나르샤 주짓수","https://www.narshacup.com/" ))
        itemList.add(Item(R.drawable.adidas,"아디다스 골든 챔스 주짓수","https://acskorea.co.kr/board/free/list.html?board_no=1" ))
        itemList.add(Item(R.drawable.royale,"리그 로얄","http://leagueroyale.co.kr/"))


        binding.recyclerView.adapter = SiteListAdapter(requireContext(), itemList)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)




    } // onCreated


}
