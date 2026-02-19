package com.example.growbox.data

import android.util.Log
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CropSyncUseCase(
    private val offlineRepository: OfflineRepository,
    private val firestore: FirebaseFirestore,
    private val userStatsUseCase: UserStatsUseCase
) {

    private var cropListener: ListenerRegistration? = null

    fun observeCropRealtime(userId: String) {
        cropListener?.remove()

        cropListener = firestore.collection("users")
            .document(userId)
            .collection("crops")
            .whereEqualTo("status", "Active")
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val doc = snapshot?.documents?.firstOrNull() ?: return@addSnapshotListener
                val remote = doc.toObject(Crop::class.java)
                    ?.copy(cropId = doc.id) ?: return@addSnapshotListener

                CoroutineScope(Dispatchers.IO).launch {
                    val local = offlineRepository.getCropOnce(userId)
                    if (local != remote) {
                        offlineRepository.insertCrop(remote, userId)
                    }
                }
            }
    }

    fun stopObserveCropRealtime() {
        cropListener?.remove()
        cropListener = null
    }

    suspend fun syncAllCrops(userId: String) {
        try {
            val snap = firestore.collection("users")
                .document(userId)
                .collection("crops")
                .get()
                .await()

            val crops = snap.documents.mapNotNull { doc ->
                doc.toObject(Crop::class.java)?.copy(cropId = doc.id)
            }

            crops.forEach { crop ->
                offlineRepository.insertCrop(crop, userId)
            }
            userStatsUseCase.recalcUserTotals(userId)
        } catch (e: Exception) {
            Log.e("CropSyncUseCase", "Failed sync", e)
        }
    }

    suspend fun fetchCurrentCrop(userId: String): Crop? {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("crops")
                .whereEqualTo("status", "Active")
                .limit(1)
                .get()
                .await()

            val doc = snapshot.documents.firstOrNull()
            val crop = doc?.toObject(Crop::class.java)?.copy(cropId = doc.id)

            crop?.let { offlineRepository.insertCrop(it, userId) }
            crop
        } catch (e: Exception) {
            Log.e("CropSyncUseCase", "Error fetching crop", e)
            null
        }
    }

    fun getLocalCropStream(userId: String): Flow<Crop?> {
        return offlineRepository.getCropStream(userId)
    }

    suspend fun getLocalCropOnce(userId: String): Crop? {
        return offlineRepository.getCropOnce(userId)
    }

    fun getAllCrops(userId: String): Flow<List<Crop>> {
        return offlineRepository.getAllCropsStream(userId)
    }
}
