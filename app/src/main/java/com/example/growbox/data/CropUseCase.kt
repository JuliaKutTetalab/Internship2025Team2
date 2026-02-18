package com.example.growbox.data

import android.util.Log
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.data.model.User
import com.google.firebase.Timestamp
import java.util.Date
import java.util.UUID

class CropUseCase(
    private val firebaseDataSource: FirebaseDataSource,
    private val offlineRepository: OfflineRepository,
    private val userStatsUseCase: UserStatsUseCase
) {

    companion object {
        private const val DEFAULT_TOTAL_DAYS = 21
        private const val MS_PER_DAY = 86_400_000L
        private const val SECONDS_PER_DAY = 86_400L
    }

    suspend fun startNewCropCycle(userId: String, cropType: String) {
        val now = Timestamp.now()
        val ends = Timestamp(Date(System.currentTimeMillis() + DEFAULT_TOTAL_DAYS.toLong() * MS_PER_DAY))

        val localActive = offlineRepository.getCropOnce(userId)

        if (localActive != null && localActive.cropId.isNotEmpty() && localActive.status == "Active") {
            val ready = isHarvestReady(localActive.startedAt, localActive.totalDays)
            if (!ready) {
                throw IllegalStateException("Crop is not ready to harvest yet")
            }

            val harvested = localActive.copy(
                status = "Harvested",
                currentDay = localActive.totalDays
            )

            offlineRepository.insertCrop(harvested, userId)
            firebaseDataSource.updateCropField(userId, localActive.cropId, "status", "Harvested")
            firebaseDataSource.updateCropField(userId, localActive.cropId, "currentDay", harvested.currentDay)
        }

        val newCropId = UUID.randomUUID().toString()
        val newCrop = Crop(
            cropId = newCropId,
            userId = userId,
            cropType = cropType,
            status = "Active",
            currentDay = 1,
            totalDays = DEFAULT_TOTAL_DAYS,
            startedAt = now,
            endsAt = ends,
            temperature = 0,
            humidity = 0,
            light = 0,
            nutrition = 0,
            isVentOn = false,
            isWateringOn = false,
            ventHours = 0,
            isLightOn = false,
            watering = 0
        )

        firebaseDataSource.saveNewCrop(userId, newCrop)
        offlineRepository.insertCrop(newCrop, userId)
    }

    suspend fun harvestAndStartNewCycle(userId: String, newCropType: String) {
        val active = offlineRepository.getCropOnce(userId) ?: run {
            startNewCropCycle(userId, newCropType)
            return
        }

        val currentDay = com.example.growbox.utils.calculateCurrentDay(active.startedAt, active.totalDays)
        if (currentDay < active.totalDays) throw IllegalStateException("Not ready to harvest")

        val now = Timestamp.now()
        val harvested = active.copy(
            status = "Harvested",
            currentDay = active.totalDays,
            endsAt = now
        )
        offlineRepository.insertCrop(harvested, userId)

        firebaseDataSource.updateCropField(userId, active.cropId, "status", "Harvested")
        firebaseDataSource.updateCropField(userId, active.cropId, "endsAt", now)

        startNewCropCycle(userId, newCropType)
        userStatsUseCase.recalcUserTotals(userId)
    }

    suspend fun markCropHarvested(userId: String, cropId: String) {
        val localCrop = offlineRepository.getCropOnce(userId)
        localCrop?.let { current ->
            val updated = current.copy(status = "Harvested")
            if (updated != current) {
                offlineRepository.insertCrop(updated, userId)
            }
        }

        try {
            firebaseDataSource.updateCropField(userId, cropId, "status", "Harvested")
        } catch (e: Exception) {
            Log.e("CropUseCase", "Remote update failed for status", e)
        }
    }

    suspend fun syncCropDay(userId: String, cropId: String) {
        val crop = offlineRepository.getCropOnce(userId) ?: return
        val started = crop.startedAt ?: return

        val nowMs = System.currentTimeMillis()
        val startedMs = started.toDate().time
        val daysPassed = ((nowMs - startedMs) / (24L * 60L * 60L * 1000L)).toInt()

        val newDay = (1 + daysPassed).coerceIn(1, crop.totalDays)
        val newStatus = if (newDay >= crop.totalDays) "Harvested" else "Active"

        if (newDay == crop.currentDay && newStatus == crop.status) return

        val updated = crop.copy(currentDay = newDay, status = newStatus)
        offlineRepository.insertCrop(updated, userId)

        userStatsUseCase.recalcUserTotals(userId)
        try {
            firebaseDataSource.updateCropField(userId, cropId, "currentDay", newDay)
            firebaseDataSource.updateCropField(userId, cropId, "status", newStatus)
        } catch (_: Exception) { }
    }

    private fun isHarvestReady(startedAt: Timestamp?, totalDays: Int): Boolean {
        if (startedAt == null) return false
        val passedSec = Timestamp.now().seconds - startedAt.seconds
        val needSec = totalDays.toLong() * SECONDS_PER_DAY
        return passedSec >= needSec
    }
}
