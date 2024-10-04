package com.syj2024.project.fragment

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.icu.text.SimpleDateFormat
import android.net.Uri
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class LogListFragment : Fragment() {

    lateinit var binding: FragmentLogListBinding
    lateinit var logListAdapter: LogListAdapter
    val logList: MutableList<Item2> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        logListAdapter = LogListAdapter(requireContext(), logList)
        binding.recyclerViewLoglist.adapter = logListAdapter


        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        binding.mcv.setTitleFormatter { day ->
            dateFormat.format(day.date) // 포맷에 맞게 날짜 제목 표시
        }

        // sqlite 열기
        val db: SQLiteDatabase =
            requireContext().openOrCreateDatabase("data", Context.MODE_PRIVATE, null)

        val currentDate = LocalDate.now()
        loadMonthlyLogs(db, currentDate.year, currentDate.monthValue)

        // 월 변경 리스너 추가
        binding.mcv.setOnMonthChangedListener { widget, date ->
            // 월 변경 시 해당 월의 데이터를 불러오기
            loadMonthlyLogs(db, date.year, date.month + 1)  // month는 0부터 시작하므로 +1
        }
    }

    // 특정 월의 데이터를 SQLite에서 조회하는 함수
    private fun loadMonthlyLogs(db: SQLiteDatabase, year: Int, month: Int) {
        // 기존 데이터 초기화
        logList.clear()


        // 해당 연도와 월에 해당하는 데이터를 쿼리로 가져오기 (yyyy-MM)
        val datePattern = "$year-$month%"

        // SQLite 쿼리 실행
        val cursor: Cursor =
            db.rawQuery("SELECT * FROM log WHERE date LIKE ?", arrayOf(datePattern))

        // 결과 처리
        cursor?.apply {
            moveToFirst()

            for (i in 0 until count) {
                var id: Int = getInt(0)
                var date = getString(1)
                var title = getString(2)
                var event = getString(3)
//                val photoPaths = getString(4)
//                val photoList = photoPaths?.split(",")?.map { Uri.parse(it) }?.toMutableList() ?: mutableListOf()

                logList.add(Item2(date, title, event))

                moveToNext()

            }
            // 커서 닫기
          cursor.close()
        }
        // 어댑터에 데이터 변경 알림
        logListAdapter.notifyDataSetChanged()
    }

}








