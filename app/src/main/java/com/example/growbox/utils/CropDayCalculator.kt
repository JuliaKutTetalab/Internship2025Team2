package com.example.growbox.utils


import com.google.firebase.Timestamp



fun calculateCurrentDay(startedAt: Timestamp?, totalDays: Int): Int {
    if (startedAt == null) return 1
    val nowSec = Timestamp.now().seconds
    val startSec = startedAt.seconds
    val daysPassed = ((nowSec - startSec) / 86_400).toInt()
    return (1 + daysPassed).coerceIn(1, totalDays)
}

fun isHarvestReady(startedAt: Timestamp?, totalDays: Int): Boolean =
    calculateCurrentDay(startedAt, totalDays) >= totalDays

