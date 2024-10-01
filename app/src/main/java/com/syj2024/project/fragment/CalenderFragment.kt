package com.syj2024.project.fragment

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.syj2024.project.R
import com.syj2024.project.activity.LogActivity
import com.syj2024.project.databinding.FragmentCalenderBinding
import com.syj2024.project.decorate.SundayDecorator
import com.syj2024.project.decorate.TodayDecorator
import java.util.Locale

class CalenderFragment : Fragment() {

    private lateinit var binding: FragmentCalenderBinding
    val selectedDayList : MutableList<CalendarDay> = mutableListOf()// 특정 이벤트 날짜 리스트
    private var selectedDate: String? = null// 현재 선택한 날짜
    val today = CalendarDay.today()



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
            .setMinimumDate(CalendarDay.from(2022, 1, 1)) // 최소 날짜 설정
            .setMaximumDate(CalendarDay.from(2100, 12, 31)) // 최대 날짜 설정
            .commit()

//        binding.mcv.selectedDate = today

//        // 날짜 선택 색상 설정
//        binding.mcv.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.select))


        binding.mcv.addDecorators(
            TodayDecorator(requireContext()),
            SundayDecorator(),

            )



        // 날짜 선택 리스너 설정
        binding.mcv.setOnDateChangedListener{ widget, date, selected ->

            selectedDate = "${date.year}-${date.month + 1}-${date.day}"
            Toast.makeText(requireContext(), "Selected Date: ${date.year}/${date.month+1}/${date.day}", Toast.LENGTH_SHORT).show()

        }

        //floating button 클릭 시 화면 전환
        binding.fab.setOnClickListener {
            if (selectedDate !=null) {
                val intent = Intent(requireContext(), LogActivity::class.java)
                intent.putExtra("selectedDate",selectedDate)
                startActivity(intent)

            }else{
                binding.mcv.selectedDate = today
                selectedDate = "${today.year}-${today.month + 1}-${today.day}"
                val intent = Intent(requireContext(),LogActivity::class.java)
                intent.putExtra("selectedDate",selectedDate)
                startActivity(intent)

            }

        }



        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        binding.mcv.setTitleFormatter { day ->
            dateFormat.format(day.date) // 포맷에 맞게 날짜 제목 표시
        }



    } // onCreate

}








