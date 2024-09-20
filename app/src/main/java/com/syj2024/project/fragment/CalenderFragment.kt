package com.syj2024.project.fragment

import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.syj2024.project.R
import com.syj2024.project.activity.LogActivity
import com.syj2024.project.databinding.FragmentCalenderBinding
import java.util.Locale

class CalenderFragment : Fragment() {

    lateinit var binding:FragmentCalenderBinding
    lateinit var calendarView: MaterialCalendarView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        calendarView = binding.mcv

        val today = CalendarDay.today()
        calendarView.selectedDate = today

        // 날짜 선택 색상 설정
        calendarView.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.select))

        // 날짜 선택 리스너 설정
        calendarView.setOnDateChangedListener { widget, date, selected ->

            Toast.makeText(requireContext(), "Selected Date: ${date.day}/${date.month+1}/${date.year}", Toast.LENGTH_SHORT).show()

            // 최소 및 최대 날짜 설정
            calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2022, 1, 1)) // 최소 날짜 설정
                .setMaximumDate(CalendarDay.from(2025, 12, 31)) // 최대 날짜 설정
                .commit()
        }


        binding.fab.setOnClickListener {
            startActivity(Intent(requireContext(), LogActivity::class.java))
        }

        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())

        calendarView.setTitleFormatter { day ->
            dateFormat.format(day.date) // 포맷에 맞게 날짜 제목 표시
        }

        calendarView.setTitleFormatter { day ->
            val year = "${day.year}년 "
            val month = "${day.month + 1}월"

            // 연도와 월을 따로 스타일링
            val title = SpannableString(year + month)

            // 연도는 빨간색으로 설정
            title.setSpan(ForegroundColorSpan(Color.BLACK), 0, year.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 월은 파란색으로 설정
            title.setSpan(ForegroundColorSpan(Color.DKGRAY), year.length, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            title
        }

        // 캘린더 뷰 내부의 TitleChanger를 통해 헤더 텍스트에 접근
        val titleContainer = calendarView.getChildAt(0) as ViewGroup

         // 헤더 배경 색상 설정
        titleContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.select))


    } // onCreate



}