package com.example.accountsbook.util

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatterBuilder

object TimeUtils {

    const val FORMAT_DATE_YYYY_MM_DD = "yyyy/MM/dd"
}

fun Long.milliToDateString(): String {
    val formatter = DateTimeFormatterBuilder()
        .appendPattern(TimeUtils.FORMAT_DATE_YYYY_MM_DD)
        .toFormatter()
    val zonedDateTime = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault())
    return zonedDateTime.format(formatter)
}
