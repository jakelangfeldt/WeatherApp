package com.jakelangfeldt.weatherapp.domain

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtilities {
    private const val DATE_PATTERN_MONTH_DAY = "MMM d"
    private const val DATE_PATTERN_DAY_OF_WEEK = "EEE"
    private const val DATE_PATTERN_TIME = "h:mma"

    fun Long.toMillis() = this * 1_000L

    fun getFormattedDate(time: Long?, timezone: String?) =
        if (time != null && timezone != null) {
            val simpleDateFormat =
                SimpleDateFormat(DATE_PATTERN_MONTH_DAY, Locale.getDefault()).apply {
                    this.timeZone = TimeZone.getTimeZone(timezone)
                    calendar.time = Date(time)
                }
            simpleDateFormat.format(simpleDateFormat.calendar.time) + " " + simpleDateFormat.calendar.getFormattedDayOfWeek()
        } else {
            ""
        }

    fun getFormattedTime(time: Long?, timezone: String?): String =
        if (time != null && timezone != null) {
            val simpleDateFormat =
                SimpleDateFormat(DATE_PATTERN_TIME, Locale.getDefault()).apply {
                    this.timeZone = TimeZone.getTimeZone(timezone)
                }
            simpleDateFormat.format(Date(time))
        } else {
            ""
        }

    fun Calendar.isInPast(timeZone: TimeZone): Boolean {
        val todayCalendar = Calendar.getInstance(timeZone, Locale.getDefault())
        return (this[Calendar.DAY_OF_YEAR] < todayCalendar[Calendar.DAY_OF_YEAR] && this[Calendar.YEAR] == todayCalendar[Calendar.YEAR]) || this[Calendar.YEAR] < todayCalendar[Calendar.YEAR]
    }

    private fun Calendar.isToday(timeZone: TimeZone): Boolean {
        val todayCalendar = Calendar.getInstance(timeZone, Locale.getDefault())
        return this[Calendar.DAY_OF_YEAR] == todayCalendar[Calendar.DAY_OF_YEAR] && this[Calendar.YEAR] == todayCalendar[Calendar.YEAR]
    }

    private fun Calendar.isTomorrow(timeZone: TimeZone): Boolean {
        val tomorrowCalendar = Calendar.getInstance(timeZone, Locale.getDefault()).apply {
            add(Calendar.DATE, 1)
        }

        return this[Calendar.DAY_OF_YEAR] == tomorrowCalendar[Calendar.DAY_OF_YEAR] && this[Calendar.YEAR] == tomorrowCalendar[Calendar.YEAR]
    }

    private fun Calendar.getFormattedDayOfWeek(): String = if (isToday(timeZone)) {
        "Today"
    } else if (isTomorrow(timeZone)) {
        "Tomorrow"
    } else {
        val simpleDateFormat =
            SimpleDateFormat(DATE_PATTERN_DAY_OF_WEEK, Locale.getDefault()).apply {
                this.timeZone = timeZone
            }
        simpleDateFormat.format(time)
    }
}
