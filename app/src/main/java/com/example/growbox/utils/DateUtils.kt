package com.example.growbox.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val DATE_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun String.toLocalDateOrNull(): LocalDate? =
    runCatching { LocalDate.parse(this.trim(), DATE_FMT) }.getOrNull()

fun LocalDate.toIsoString(): String = format(DATE_FMT)

fun dayOfWeekShort(date: LocalDate): String = when (date.dayOfWeek.value) {
    1 -> "Mon"
    2 -> "Tue"
    3 -> "Wed"
    4 -> "Thu"
    5 -> "Fri"
    6 -> "Sat"
    else -> "Sun"
}

fun nowLocalDate(): LocalDate = LocalDate.now(ZoneId.systemDefault())
