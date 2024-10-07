package com.syj2024.project.fragment

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.syj2024.project.R
import com.syj2024.project.activity.LogActivity
import com.syj2024.project.databinding.FragmentCalenderBinding
import com.syj2024.project.decorate.EventDecorator
import com.syj2024.project.decorate.SundayDecorator
import com.syj2024.project.decorate.TodayDecorator
import java.time.LocalDate
import java.util.Locale

class CalenderFragment : Fragment() {

    private lateinit var binding: FragmentCalenderBinding
    val eventDays : MutableList<CalendarDay> = mutableListOf()// 특정 이벤트 날짜 리스트
    private var selectedDate: String? = null// 현재 선택한 날짜
    val today = CalendarDay.today()
    var selectedDay:CalendarDay?= null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalenderBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 최소 및 최대 날짜 설정
             binding.mcv.state().edit()
            .setMinimumDate(CalendarDay.from(2021, 12, 1)) // 최소 날짜 설정
            .setMaximumDate(CalendarDay.from(2100, 12, 31)) // 최대 날짜 설정
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()


        binding.mcv.selectedDate = today

        // 날짜 선택 색상 설정
        binding.mcv.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.select))

        // 오늘 날짜 데코레이터 추가
        binding.mcv.addDecorator(TodayDecorator(requireContext()))



        // SQLite에서 이벤트 날짜 가져오기
        val eventDays = getEventDaysFromDatabase()

        binding.mcv.addDecorator(EventDecorator(Color.RED,eventDays))



        // 날짜 선택 리스너 설정
        binding.mcv.setOnDateChangedListener{ widget, date, selected ->

            selectedDate = "${date.year}-${date.month + 1}-${date.day}"
            Toast.makeText(requireContext(), "Selected Date: ${date.year}/${date.month+1}/${date.day}", Toast.LENGTH_SHORT).show()

        }

        // floating button 클릭 시 LogActivity에 데이터 전송
        binding.fab.setOnClickListener {
            val intent = Intent(requireContext(), LogActivity::class.java)
            if (selectedDate != null) {
                intent.putExtra("selectedDate", selectedDate)
            } else {
                selectedDate = "${today.year}-${today.month + 1}-${today.day}"
                intent.putExtra("selectedDate", selectedDate)
            }
            startActivity(intent)
        }


        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        binding.mcv.setTitleFormatter { day ->
            dateFormat.format(day.date) // 포맷에 맞게 날짜 제목 표시
        }

    } // onCreate


    fun getEventDaysFromDatabase(): List<CalendarDay> {
        // SQLite 데이터베이스 열기 (혹은 생성)
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("data", Context.MODE_PRIVATE, null)

        // log 테이블에서 date 데이터를 가져오는 쿼리 실행
        val cursor = db.rawQuery("SELECT date FROM log", null)

        // 가져온 날짜 데이터를 저장할 리스트
        val eventDays = mutableListOf<CalendarDay>()

        if (cursor.moveToFirst()) {
            do {
                // date 문자열 가져오기
                val dateString = cursor.getString(cursor.getColumnIndexOrThrow("date"))

                // 날짜 문자열을 "yyyy-MM-dd" 형식으로 나누기
                val parts = dateString.split("-")
                val year = parts[0].toInt()
                val month = parts[1].toInt()
                val day = parts[2].toInt()

                // CalendarDay 객체로 변환한 후 리스트에 추가
                eventDays.add(CalendarDay.from(year, month-1, day))
            } while (cursor.moveToNext())
        }

        // Cursor 및 데이터베이스 닫기
        cursor.close()
        db.close()

        return eventDays
    }

}








