package com.pefdneves.mydots.utils

import android.content.Context
import com.pefdneves.mydots.R

object TimeUtils {

    fun getHoursAndMinutesFromMinutesReadable(totalInMinutes: Int, context: Context): String {
        var hours = totalInMinutes / 60
        var minutes = totalInMinutes % 60
        if (hours < 0 || minutes < 0) {
            hours = 0
            minutes = 0
        }
        var string = context.getString(
            R.string.overview_battery_time_unknown
        )
        if (minutes in 1..60 && hours in 1..100) {
            string =
                context.getString(
                    R.string.overview_battery_in_time_hours_and_minutes,
                    hours,
                    minutes
                )
        } else if (hours < 1 && minutes in 1..60) {
            string =
                context.getString(
                    R.string.overview_battery_in_time_minutes,
                    minutes
                )
        } else if (hours in 1..100 && minutes < 1) {
            string =
                context.getString(
                    R.string.overview_battery_in_time_hours,
                    hours
                )
        }
        return string
    }

}