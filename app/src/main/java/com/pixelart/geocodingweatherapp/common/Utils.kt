package com.pixelart.geocodingweatherapp.common

import java.text.SimpleDateFormat
import java.util.*

enum class Utils {
    INSTANCE;

    fun timestampToDateTime(timestamp: Long): String{
        val calender = Calendar.getInstance()
        val timeZone = calender.timeZone
        val dateFormat = SimpleDateFormat("E, dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
        calender.timeInMillis = timestamp * 1000L
        dateFormat.timeZone = timeZone

        return dateFormat.format(calender.time)
    }

    fun timestampToDayLong(timestamp: Long): String{
        val calender = Calendar.getInstance()
        val timeZone = calender.timeZone
        val dateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
        calender.timeInMillis = timestamp * 1000L
        dateFormat.timeZone = timeZone

        return dateFormat.format(calender.time)
    }

    fun timestampToDayShort(timestamp: Long): String{
        val calender = Calendar.getInstance()
        val timeZone = calender.timeZone
        val dateFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
        calender.timeInMillis = timestamp * 1000L
        dateFormat.timeZone = timeZone

        return dateFormat.format(calender.time)
    }
}