package com.jakelangfeldt.weatherapp.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtils {
    companion object {
        private const val DATE_PATTERN = "MMM d"

        fun getFormattedDate(time: Long?, timezone: String?) =
            if (time != null && timezone != null) {
                SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone(timezone)
                }.format(Date(time * 1_000L)).toString()
            } else {
                ""
            }
    }
}
