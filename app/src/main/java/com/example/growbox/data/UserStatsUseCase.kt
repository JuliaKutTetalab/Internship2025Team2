package com.example.growbox.data

import android.util.Log
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.extension.calcGrownDays
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class UserStatsUseCase(
    private val offlineRepository: OfflineRepository,
    private val firestore: FirebaseFirestore
) {

    suspend fun recalcUserTotals(userId: String) {
        val crops = offlineRepository.getAllCropsStream(userId).first()

        val totalHarvestCount = crops.count { it.status.equals("Harvested", ignoreCase = true) }
        val totalDaysGrown = crops.sumOf { calcGrownDays(it) }

        try {
            firestore.collection("users")
                .document(userId)
                .set(
                    mapOf(
                        "totalHarvestCount" to totalHarvestCount,
                        "totalDaysGrown" to totalDaysGrown
                    ),
                    SetOptions.merge()
                )
                .await()
        } catch (e: Exception) {
            Log.e("UserStatsUseCase", "Failed to update firestore totals", e)
        }
    }
}
