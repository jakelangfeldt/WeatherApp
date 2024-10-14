package com.jakelangfeldt.weatherapp.domain

import com.jakelangfeldt.weatherapp.domain.DateUtilities.isInPast
import com.jakelangfeldt.weatherapp.domain.DateUtilities.toMillis
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtilitiesTest {

    @Test
    fun `toMillis should convert long correctly`() {
        val input1 = 1728673200L
        val input2 = 1728759600L
        val input3 = 1728846000L

        val result1 = input1.toMillis()
        val result2 = input2.toMillis()
        val result3 = input3.toMillis()

        assertEquals(input1 * 1_000, result1)
        assertEquals(input2 * 1_000, result2)
        assertEquals(input3 * 1_000, result3)
    }

    @Test
    fun `getFormattedDate should return correct date`() {
        val timezone = "America/Los_Angeles"

        val input1 = (1728673200L).toMillis()
        val input2 = (1728759600L).toMillis()
        val input3 = (1728846000L).toMillis()

        val result1 = DateUtilities.getFormattedDate(input1, timezone)
        val result2 = DateUtilities.getFormattedDate(input2, timezone)
        val result3 = DateUtilities.getFormattedDate(input3, timezone)

        assertEquals("Oct 11 Fri", result1)
        assertEquals("Oct 12 Sat", result2)
        assertEquals("Oct 13 Sun", result3)
    }

    @Test
    fun `getFormattedTime should return correct time`() {
        val timezone = "America/Los_Angeles"

        val input1 = (1728914295L).toMillis()
        val input2 = (1728932400L).toMillis()
        val input3 = (1728955222L).toMillis()

        val result1 = DateUtilities.getFormattedTime(input1, timezone)
        val result2 = DateUtilities.getFormattedTime(input2, timezone)
        val result3 = DateUtilities.getFormattedTime(input3, timezone)

        assertEquals("6:58AM", result1)
        assertEquals("12:00PM", result2)
        assertEquals("6:20PM", result3)
    }

    @Test
    fun `isInPast should identify past time correctly`() {
        val timeZone = TimeZone.getTimeZone("America/Los_Angeles")

        val input1 = Calendar.getInstance(timeZone, Locale.getDefault())
            .apply { this.time = Date((1728673200L).toMillis()) }
        val input2 = Calendar.getInstance(timeZone, Locale.getDefault())
            .apply { this.time = Date((1728759600L).toMillis()) }
        val input3 = Calendar.getInstance(timeZone, Locale.getDefault())
            .apply { this.time = Date((1728846000L).toMillis()) }

        val result1 = input1.isInPast(timeZone)
        val result2 = input2.isInPast(timeZone)
        val result3 = input3.isInPast(timeZone)

        assertTrue(result1)
        assertTrue(result2)
        assertTrue(result3)
    }
}
