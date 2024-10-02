package com.syj2024.project.decorate

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.syj2024.project.R

class TodayDecorator(val context: Context) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day== CalendarDay.today()
    }

    override fun decorate(view: DayViewFacade?) {
        val drawable= ContextCompat.getDrawable(context, R.drawable.today_circle)
        view?.setBackgroundDrawable(drawable!!)   //배경 그림
        view?.addSpan(object : ForegroundColorSpan(Color.WHITE){})  //글씨 색상
    }
}