package com.example.growbox.data

import android.util.Log
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SensorUseCase(
    private val firebaseDataSource: FirebaseDataSource,
    private val offlineRepository: OfflineRepository,
    private val firestore: FirebaseFirestore
) {

    private suspend fun updateCropLocalAndRemote(
        userId: String,
        cropId: String,
        fieldName: String,
        remoteValue: Any,
        updateLocal: (Crop) -> Crop
    ) {
        val localCrop = offlineRepository.getCropOnce(userId)
        localCrop?.let { current ->
            val updated = updateLocal(current)
            if (updated != current) {
                offlineRepository.insertCrop(updated, userId)
            }
        }

        try {
            firebaseDataSource.updateCropField(userId, cropId, fieldName, remoteValue)
        } catch (e: Exception) {
            Log.e("SensorUseCase", "Remote update failed for $fieldName", e)
        }
    }

    private suspend fun writeHourlySnapshot(userId: String, cropId: String) {
        val crop = offlineRepository.getCropOnce(userId) ?: return
        val data = hashMapOf(
            "createdAt" to com.google.firebase.Timestamp.now(),
            "temperature" to crop.temperature,
            "humidity" to crop.humidity,
            "light" to crop.light,
            "nutrition" to crop.nutrition
        )
        try {
            firestore.collection("users")
                .document(userId)
                .collection("crops")
                .document(cropId)
                .collection("hourly")
                .document()
                .set(data)
                .await()
        } catch (_: Exception) { }
    }

    private suspend fun writeDailyHistory(userId: String, cropId: String) {
        val crop = offlineRepository.getCropOnce(userId) ?: return
        val today = java.time.LocalDate.now().toString()
        val dayLabel = java.time.LocalDate.now().dayOfWeek.name.take(3).lowercase()
            .replaceFirstChar { it.uppercase() }

        val data = hashMapOf(
            "date" to today,
            "dayLabel" to dayLabel,
            "temperature" to crop.temperature,
            "humidity" to crop.humidity,
            "light" to crop.light,
            "nutrition" to crop.nutrition,
            "lightUsage" to crop.light,
            "tempUsage" to crop.temperature,
            "waterUsage" to crop.humidity,
            "nutritionUsage" to crop.nutrition
        )
        try {
            firestore.collection("users")
                .document(userId)
                .collection("crops")
                .document(cropId)
                .collection("history")
                .document(today)
                .set(data)
                .await()
        } catch (_: Exception) { }
    }

    suspend fun updateVentStatus(userId: String, cropId: String, isOn: Boolean) {
        updateCropLocalAndRemote(userId, cropId, "isVentOn", isOn) {
            it.copy(isVentOn = isOn)
        }
    }

    suspend fun updateVentHours(userId: String, cropId: String, hours: Int) {
        updateCropLocalAndRemote(userId, cropId, "ventHours", hours) {
            it.copy(ventHours = hours)
        }
    }

    suspend fun updateLightStatus(userId: String, cropId: String, isOn: Boolean) {
        updateCropLocalAndRemote(userId, cropId, "isLightOn", isOn) {
            it.copy(isLightOn = isOn)
        }
    }

    suspend fun updateLightValue(userId: String, cropId: String, value: Int) {
        updateCropLocalAndRemote(userId, cropId, "light", value) {
            it.copy(light = value)
        }
        writeHourlySnapshot(userId, cropId)
        writeDailyHistory(userId, cropId)
    }

    suspend fun updateCurrentTemperature(userId: String, cropId: String, temp: Int) {
        updateCropLocalAndRemote(userId, cropId, "temperature", temp) {
            it.copy(temperature = temp)
        }
        writeHourlySnapshot(userId, cropId)
        writeDailyHistory(userId, cropId)
    }

    suspend fun updateCurrentHumidity(userId: String, cropId: String, humidity: Int) {
        updateCropLocalAndRemote(userId, cropId, "humidity", humidity) {
            it.copy(humidity = humidity)
        }
        writeHourlySnapshot(userId, cropId)
        writeDailyHistory(userId, cropId)
    }

    suspend fun updateNutritionValue(userId: String, cropId: String, mg: Int) {
        updateCropLocalAndRemote(userId, cropId, "nutrition", mg) {
            it.copy(nutrition = mg)
        }
        writeHourlySnapshot(userId, cropId)
        writeDailyHistory(userId, cropId)
    }

    suspend fun updateWateringStatus(userId: String, cropId: String, isOn: Boolean) {
        updateCropLocalAndRemote(userId, cropId, "isWateringOn", isOn) {
            it.copy(isWateringOn = isOn)
        }
    }

    suspend fun updateWateringValue(userId: String, cropId: String, value: Int) {
        updateCropLocalAndRemote(userId, cropId, "watering", value) {
            it.copy(watering = value)
        }
    }
}
