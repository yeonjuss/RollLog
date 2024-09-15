package com.syj2024.project.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import com.syj2024.project.R
import com.syj2024.project.databinding.ActivityMainBinding
import com.syj2024.project.fragment.CompetitonListFragment
import com.syj2024.project.fragment.NearbyGymFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val calendarView = findViewById<MaterialCalendarView>(R.id.mcv)

        // 날짜 선택 색상 설정
        calendarView.setSelectionColor(ContextCompat.getColor(this, R.color.select))

        // 날짜 선택 리스너 설정
        calendarView.setOnDateChangedListener{ widget, date, selected ->


            // 날짜 선택 시 실행될 동작
            Toast.makeText(this, "Selected Date: ${date.day}/${date.month+1}/${date.year}",Toast.LENGTH_SHORT).show()



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


        binding.bnv.setOnItemSelectedListener { menuItem ->
            // 선택된 탭에 따라 반응
            when(menuItem.itemId) {
                R.id.bnv_rc -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container1, CompetitonListFragment()).commit()
                R.id.bnv_sd -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container1, CompetitonListFragment()).commit()
                R.id.bnv_search -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container1, NearbyGymFragment()).commit()

            }

            // 리턴값을 true로 해야 선택변경에 따른 UI가 반영됨
            true
        }


    }// onCreate

} //MainActivity //




