package com.syj2024.project.decorate

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.time.DayOfWeek
import java.time.LocalDate

class SundayDecorator : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        val sunday:Int= LocalDate.of(day!!.year, day.month, day.day).with(DayOfWeek.SUNDAY).dayOfMonth
        return day.day == sunday

    }

    override fun decorate(view: DayViewFacade?) {

        view?.addSpan(object : ForegroundColorSpan(Color.RED){})
    }
}
