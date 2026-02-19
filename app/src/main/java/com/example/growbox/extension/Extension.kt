package com.example.growbox.extension

import com.example.growbox.data.model.Crop

fun String.toDayNumber(): String =
    this.split("-").lastOrNull()?.padStart(2, '0') ?: ""

 fun calcGrownDays(crop: Crop): Int {
    val started = crop.startedAt?.toDate()?.time ?: return 0

    val endMs = when {
        crop.status.equals("Harvested", ignoreCase = true) && crop.endsAt != null ->
            crop.endsAt!!.toDate().time
        else ->
            System.currentTimeMillis()
    }

    val diff = (endMs - started).coerceAtLeast(0L)
    val daysPassed = (diff / 86_400_000L).toInt()


    return (1 + daysPassed).coerceIn(1, crop.totalDays)
}

