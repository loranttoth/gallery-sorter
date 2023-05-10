package com.loranttoth.gallerysorter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.loranttoth.gallerysorter.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar)

        binding.cv.date = MainActivity.myViewModel.actTime

        binding.cv.setOnDateChangeListener { calendarView, year, month, date ->
            MainActivity.myViewModel.setDate(year, month, date)
            super.finish()
        }
    }
}