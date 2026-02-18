package com.example.growbox.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

class MockDataSeeder(
    private val firestore: FirebaseFirestore
) {

    private val dateFmt: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val CROPS_COLLECTION = "crops"
        private const val HOUR_COLLECTION = "hourly"
        private const val HOURS_PER_DAY = 24
        private const val MAX_PERCENT = 100
    }

    suspend fun seedCropMock_2026_02_05_to_02_16(userId: String) {
        val cropId = "testCrop"
        val zone = ZoneId.systemDefault()

        val cropsRef = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)

        val cropRef = cropsRef.document(cropId)
        val historyRef = cropRef.collection("history")
        val hourlyRef = cropRef.collection(HOUR_COLLECTION)

        val startDate = LocalDate.of(2026, 2, 5)
        val endDate = LocalDate.of(2026, 2, 16)
        val daysCount = ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1

        val startedAtZdt: ZonedDateTime = startDate.atStartOfDay(zone)
        val endsAtZdt: ZonedDateTime = endDate.atTime(23, 59, 59).atZone(zone)

        val startedAtTs = Timestamp(Date(startedAtZdt.toInstant().toEpochMilli()))
        val endsAtTs = Timestamp(Date(endsAtZdt.toInstant().toEpochMilli()))

        val cropDoc = hashMapOf(
            "cropId" to cropId,
            "userId" to userId,
            "cropType" to "Herbs",
            "status" to "Active",
            "currentDay" to 3,
            "totalDays" to 21,
            "startedAt" to startedAtTs,
            "endsAt" to endsAtTs,
            "temperature" to 25,
            "humidity" to 33,
            "light" to 32,
            "nutrition" to 65,
            "isVentOn" to true,
            "isWateringOn" to true,
            "ventHours" to 49,
            "isLightOn" to false,
            "watering" to 70,
            "lightRecommended" to 72,
            "tempRecommended" to 26,
            "humidityRecommended" to 74,
            "nutritionRecommended" to 65
        )

        cropRef.set(cropDoc).await()

        for (i in 0 until daysCount) {
            val d = startDate.plusDays(i.toLong())
            val dateStr = d.format(dateFmt)

            val light = ((20 + (i * 7)) % MAX_PERCENT).coerceIn(0, 100)
            val temp = (18 + (i % 10)).coerceIn(0, 50)
            val hum = (35 + (i * 2)).coerceIn(0, 100)
            val nutr = ((40 + (i * 4)) % MAX_PERCENT).coerceIn(0, 100)

            val dayLabel = d.dayOfWeek.name.take(3).lowercase()
                .replaceFirstChar { it.uppercase() }

            val historyDoc = hashMapOf(
                "date" to dateStr,
                "dayLabel" to dayLabel,
                "light" to light,
                "temperature" to temp,
                "humidity" to hum,
                "nutrition" to nutr,
                "lightUsage" to (1..10).random(),
                "tempUsage" to (10..40).random(),
                "waterUsage" to (10..80).random(),
                "nutritionUsage" to (1..50).random()
            )

            historyRef.document(dateStr).set(historyDoc).await()
        }

        for (dayIndex in 0 until daysCount) {
            val day = startDate.plusDays(dayIndex.toLong())
            val dayStart = day.atStartOfDay(zone)

            for (h in 0 until HOURS_PER_DAY) {
                val zdt = dayStart.plusHours(h.toLong())
                val ts = Timestamp(Date(zdt.toInstant().toEpochMilli()))

                val light = when {
                    h in 0..5 -> 5 + h * 2
                    h in 6..11 -> 40 + (h - 6) * 8
                    h in 12..17 -> 90 - (h - 12) * 6
                    else -> 30 - (h - 18) * 4
                }.coerceIn(0, MAX_PERCENT)

                val temp = (16 + (h % 10)).coerceIn(0, 50)
                val hum = (45 + (h % 20)).coerceIn(0, 100)
                val nutr = ((50 + (h * 3) % MAX_PERCENT)).coerceIn(0, 100)

                val hourlyDoc = hashMapOf(
                    "createdAt" to ts,
                    "temperature" to temp,
                    "humidity" to hum,
                    "light" to light,
                    "nutrition" to nutr
                )

                hourlyRef.document().set(hourlyDoc).await()
            }
        }
    }
}
