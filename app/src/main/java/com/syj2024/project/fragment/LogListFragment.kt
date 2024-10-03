package com.syj2024.project.fragment

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.syj2024.project.adapter.LogListAdapter
import com.syj2024.project.databinding.FragmentLogListBinding
import java.util.Locale


class LogListFragment : Fragment() {

    lateinit var binding: FragmentLogListBinding
    lateinit var logListAdapter: LogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLogListBinding.inflate(inflater,container,false)
        return binding.root
    }

    val logList: MutableList<Item2> = mutableListOf()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logListAdapter = LogListAdapter(requireContext(), logList)
        binding.recyclerViewLoglist.adapter = logListAdapter


       val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        binding.mcv.setTitleFormatter { day ->
            dateFormat.format(day.date) // 포맷에 맞게 날짜 제목 표시
        }

      // sqlite 열기
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("data", Context.MODE_PRIVATE, null)

        val currentDate = CalendarDay.today()
        loadMonthlyLogs(db, currentDate.year, currentDate.month + 1)

        binding.mcv.setOnMonthChangedListener { _, date ->
            Toast.makeText(requireContext(), "${date.month+1}월", Toast.LENGTH_SHORT).show()
            loadMonthlyLogs(db, currentDate.year, currentDate.month + 1) // month는 0부터 시작하므로 +1
        }
    }




    private fun loadMonthlyLogs(db: SQLiteDatabase, year: Int, month: Int) {
        // 기존 리스트 초기화
        logList.clear()

        // 월별 기록을 불러오는 쿼리
        val monthString = if (month < 10) "0$month" else month.toString()  // 1자리 숫자를 2자리로 변환
        val datePattern = "$year-$monthString%"  // 'yyyy-MM%' 형식으로 쿼리

        val cursor: Cursor = db.rawQuery("SELECT * FROM log WHERE date LIKE ?", arrayOf(datePattern))


//      val cursor: Cursor = db.rawQuery("SELECT * FROM log", null)

        //한 줄씩 읽어오기
        cursor?.apply {
            if (moveToFirst()) {
                do {
                    val date = getString(getColumnIndexOrThrow("date"))
                    val title = getString(getColumnIndexOrThrow("title"))
                    val event = getString(getColumnIndexOrThrow("event"))

                    // 리스트에 추가
                    logList.add(Item2(date, title, event))
                } while (moveToNext())
            }

            // 커서 닫기
            cursor.close()
        }

        // 어댑터에 데이터가 변경되었음을 알림
        logListAdapter.notifyDataSetChanged()

    }

}

      //OnCreateView







