package net.catstack.nfcpay.common.utils

import java.text.SimpleDateFormat
import java.util.*

fun getHistoryDateFormat(dateTime: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

    val date = dateFormat.parse(dateTime)

    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = date

    val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

    val month = when (cal.get(Calendar.MONTH)) {
        Calendar.JANUARY -> "января"
        Calendar.FEBRUARY -> "февраля"
        Calendar.MARCH -> "марта"
        Calendar.APRIL -> "апреля"
        Calendar.MAY -> "мая"
        Calendar.JUNE -> "июня"
        Calendar.JULY -> "июля"
        Calendar.AUGUST -> "августа"
        Calendar.SEPTEMBER -> "сентября"
        Calendar.OCTOBER -> "октября"
        Calendar.NOVEMBER -> "ноября"
        Calendar.DECEMBER -> "декабря"
        else -> ""
    }

    val week = when (cal.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "вс"
        Calendar.MONDAY -> "пн"
        Calendar.TUESDAY -> "вт"
        Calendar.WEDNESDAY -> "ср"
        Calendar.THURSDAY -> "чт"
        Calendar.FRIDAY -> "пт"
        Calendar.SATURDAY -> "вс"
        else -> ""
    }

    return "$dayOfMonth $month, $week"
}

fun getHistoryDateAndTimeFormat(dateTime: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

    val date = dateFormat.parse(dateTime)

    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = date

    val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
    val month = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
    val year = cal.get(Calendar.YEAR)
    val hours = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
    val minutes = cal.get(Calendar.MINUTE).toString().padStart(2, '0')

    return "$dayOfMonth.$month.$year $hours:$minutes"
}