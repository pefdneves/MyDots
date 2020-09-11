package com.pefdneves.mydots.utils

import android.content.Context
import com.pefdneves.mydots.R
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilsTest {

    @Test
    fun test_getHoursAndMinutesFromMinutesReadable_invalid() {
        val minutes = -1
        val context = mockk<Context>()
        val string = "String"
        every {
            context.getString(
                R.string.overview_battery_time_unknown
            )
        } returns string

        val result = TimeUtils.getHoursAndMinutesFromMinutesReadable(minutes, context)

        assertEquals(string, result)
    }

    @Test
    fun test_getHoursAndMinutesFromMinutesReadable_hourNoMinutes() {
        val minutes = 60
        val hours = 1
        val context = mockk<Context>()
        val string = "String"
        every {
            context.getString(
                R.string.overview_battery_time_unknown
            )
        } returns "unknown"
        every {
            context.getString(
                R.string.overview_battery_in_time_hours,
                hours
            )
        } returns string

        val result = TimeUtils.getHoursAndMinutesFromMinutesReadable(minutes, context)

        assertEquals(string, result)
    }

    @Test
    fun test_getHoursAndMinutesFromMinutesReadable_minutesNoHours() {
        val minutes = 59
        val context = mockk<Context>()
        val string = "String"
        every {
            context.getString(
                R.string.overview_battery_time_unknown
            )
        } returns "unknown"
        every {
            context.getString(
                R.string.overview_battery_in_time_minutes,
                minutes
            )
        } returns string

        val result = TimeUtils.getHoursAndMinutesFromMinutesReadable(minutes, context)

        assertEquals(string, result)
    }

    @Test
    fun test_getHoursAndMinutesFromMinutesReadable_minutesAndHours() {
        val minutesTotal = 61
        val hours = 1
        val minutes = 1
        val context = mockk<Context>()
        val string = "String"
        every {
            context.getString(
                R.string.overview_battery_time_unknown
            )
        } returns "unknown"
        every {
            context.getString(
                R.string.overview_battery_in_time_hours_and_minutes,
                hours,
                minutes
            )
        } returns string

        val result = TimeUtils.getHoursAndMinutesFromMinutesReadable(minutesTotal, context)

        assertEquals(string, result)
    }

}