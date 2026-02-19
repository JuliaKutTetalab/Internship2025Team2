package com.example.growbox.data

import android.util.Log
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ChartDataUseCase(
    private val firebaseDataSource: FirebaseDataSource,
    private val offlineRepository: OfflineRepository,
    private val firestore: FirebaseFirestore
) {

    fun getCropHistory(
        userId: String,
        cropId: String,
        type: ChartType
    ): Flow<List<ChartDataPoint>> = flow {
        val localHistory =
            offlineRepository.getChartHistoryStream(cropId, type.name).first()
        emit(localHistory)

        try {
            val snapshots = firestore.collection("users")
                .document(userId)
                .collection("crops")
                .document(cropId)
                .collection("history")
                .orderBy("date")
                .get()
                .await()

            val usageField = when (type) {
                ChartType.LIGHT -> "lightUsage"
                ChartType.TEMPERATURE -> "tempUsage"
                ChartType.HUMIDITY -> "waterUsage"
                ChartType.NUTRITION -> "nutritionUsage"
            }

            val valueField = when (type) {
                ChartType.LIGHT -> "light"
                ChartType.TEMPERATURE -> "temperature"
                ChartType.HUMIDITY -> "humidity"
                ChartType.NUTRITION -> "nutrition"
            }

            val remoteHistory = snapshots.documents.map { doc ->
                ChartDataPoint(
                    value = doc.getDouble(valueField)?.toFloat() ?: 0f,
                    dayLabel = doc.getString("dayLabel") ?: "",
                    dataLabel = doc.getString("date") ?: "",
                    usageValue = doc.getDouble(usageField)?.toFloat() ?: 0f
                )
            }

            if (remoteHistory.isNotEmpty()) {
                offlineRepository.insertChartHistory(cropId, type.name, remoteHistory)
                emit(remoteHistory)
            }
        } catch (e: Exception) {
            Log.e("ChartDataUseCase", "Error fetching history", e)
        }
    }

    fun getCropHourly(
        userId: String,
        cropId: String,
        type: ChartType
    ): Flow<List<ChartDataPoint>> = flow {
        val since = System.currentTimeMillis() - 24L * 60 * 60 * 1000
        val localHourly = offlineRepository.getChartHourlyStream(cropId, type.name, since).first()
        emit(localHourly)

        try {
            val remotePoints = firebaseDataSource.fetchCropHourly(userId, cropId, type)
            if (remotePoints.isNotEmpty()) {
                val now = System.currentTimeMillis()
                val createdAtList = remotePoints.map { point ->
                    val h = point.hour ?: 0
                    val cal = java.util.Calendar.getInstance()
                    cal.set(java.util.Calendar.HOUR_OF_DAY, h)
                    cal.set(java.util.Calendar.MINUTE, 0)
                    cal.set(java.util.Calendar.SECOND, 0)
                    if (cal.timeInMillis > now) cal.add(java.util.Calendar.DAY_OF_MONTH, -1)
                    cal.timeInMillis
                }
                offlineRepository.insertChartHourly(cropId, type.name, remotePoints, createdAtList)
                emit(remotePoints)
            }
        } catch (e: Exception) {
            Log.e("ChartDataUseCase", "Error fetching hourly", e)
        }
    }
}
