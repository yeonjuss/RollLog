package com.syj2024.project.fragment


import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.syj2024.project.adapter.LogListAdapter
import com.syj2024.project.adapter.SiteListAdapter
import com.syj2024.project.databinding.FragmentLogListBinding

class LogListFragment: Fragment() {
    lateinit var binding: FragmentLogListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogListBinding.inflate(inflater, container, false)
        return binding.root

    }

    val logList: MutableList<Item2> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db: SQLiteDatabase =
            requireContext().openOrCreateDatabase("data", Context.MODE_PRIVATE, null)
        val cursor: Cursor = db.rawQuery("SELECT * FROM log", null)

        //한 줄씩 읽어오기
        cursor?.apply {
            moveToFirst()

            for (i in 0 until count) {
                var id: Int = getInt(0)
                var date = getString(1)
                var title = getString(2)
                var event = getString(3)

                logList.add(Item2(date, title, event))

                moveToNext()

            }

            binding.recyclerViewLoglist.adapter = LogListAdapter(requireContext(), logList)


        }



    }
}




