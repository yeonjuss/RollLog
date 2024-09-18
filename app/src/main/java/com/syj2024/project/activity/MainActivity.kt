package com.syj2024.project.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import com.syj2024.project.R
import com.syj2024.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val calendarView = findViewById<MaterialCalendarView>(R.id.mcv)

        // 날짜 선택 색상 설정
        calendarView.setSelectionColor(ContextCompat.getColor(this, R.color.select))

        // 날짜 선택 리스너 설정
        calendarView.setOnDateChangedListener { widget, date, selected ->
            // 날짜 선택 시 실행될 동작
            Toast.makeText(this, "Selected Date: ${date.day}/${date.month}/${date.year}",Toast.LENGTH_SHORT).show()

        class EventDecorator(private val color: Int, private val dates: HashSet<CalendarDay>) :
            DayViewDecorator {

            override fun shouldDecorate(day: CalendarDay?): Boolean {
                return dates.contains(day)
            }

            override fun decorate(view: DayViewFacade?) {
                view?.addSpan(DotSpan(8f, color)) // 날짜 아래 점을 추가
            }
        }

        calendarView.state().edit()
            .setMinimumDate(CalendarDay.from(2022, 1, 1)) // 최소 날짜 설정
            .setMaximumDate(CalendarDay.from(2025, 12, 31)) // 최대 날짜 설정
            .commit()


    }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java))
        }

    }// onCreate
} //MainActivity //




