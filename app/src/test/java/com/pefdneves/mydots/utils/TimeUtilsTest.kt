package com.pefdneves.mydots.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilsTest {

    @Test
    fun test_getHoursAndMinutesFromMinutes_valid() {
        val minutes = 90

        val result = TimeUtils.getHoursAndMinutesFromMinutes(minutes)

        assertEquals(result.first, 1)
        assertEquals(result.second, 30)
    }

    @Test
    fun test_getHoursAndMinutesFromMinutes_invalid() {
        val minutes = -1

        val result = TimeUtils.getHoursAndMinutesFromMinutes(minutes)

        assertEquals(result.first, 0)
        assertEquals(result.second, 0)
    }

    @Test
    fun test_getHoursAndMinutesFromMinutes_zero() {
        val minutes = 0

        val result = TimeUtils.getHoursAndMinutesFromMinutes(minutes)

        assertEquals(result.first, 0)
        assertEquals(result.second, 0)
    }
}