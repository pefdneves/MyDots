package com.pefdneves.mydots.utils

object TimeUtils {

    fun getHoursAndMinutesFromMinutes(totalInMinutes: Int): Pair<Int, Int> {
        var hours = totalInMinutes / 60
        var minutes = totalInMinutes % 60
        if (hours < 0 || minutes < 0) {
            hours = 0
            minutes = 0
        }
        return Pair(hours, minutes)
    }

}