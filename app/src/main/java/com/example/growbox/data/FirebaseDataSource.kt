package com.example.growbox.data

import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.User
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartType
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface FirebaseDataSource {

    suspend fun registerUser(email: String, password: String): String

    suspend fun signIn(email: String, password: String): String

    suspend fun isLoggedIn(): Boolean

    fun getCurrentUserId(): String?

    fun getCurrentUserEmail(): String?

    suspend fun signOut()

    suspend fun createUserProfile(userId: String, email: String)

    suspend fun saveNewCrop(userId: String, crop: Crop)

    suspend fun fetchCurrentCrop(userId: String): Crop?

    suspend fun updateCropField(userId: String, cropId: String, field: String, value: Any)

    suspend fun fetchCropHistory(userId: String, cropId: String, type: ChartType): List<ChartDataPoint>

    suspend fun fetchCropHourly(userId: String, cropId: String, type: ChartType): List<ChartDataPoint>

    suspend fun seedCropMock_2026_02_05_to_02_16(userId: String)
}

class FirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseDataSource {

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val CROPS_COLLECTION = "crops"
        private const val HOUR_COLLECTION = "hourly"
        private const val MS_24H = 24L * 60L * 60L * 1000L
    }

    private val mockDataSeeder by lazy { MockDataSeeder(firestore) }

    override suspend fun registerUser(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw IllegalStateException("UID is null")
    }

    override suspend fun signIn(email: String, password: String): String {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        if (userId != null) continuation.resume(userId)
                        else continuation.resumeWithException(IllegalStateException("User ID is null"))
                    } else {
                        continuation.resumeWithException(task.exception ?: Exception("Sign-in failed"))
                    }
                }
        }
    }

    override suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        auth.currentUser != null
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid

    override fun getCurrentUserEmail(): String? = auth.currentUser?.email

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun createUserProfile(userId: String, email: String) {
        val initialUser = User(
            userId = userId,
            email = email,
            farmName = "GrowBox-${userId.take(5)}"
        )
        firestore.collection(USERS_COLLECTION).document(userId).set(initialUser).await()
    }

    override suspend fun saveNewCrop(userId: String, crop: Crop) {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(crop.cropId)
            .set(crop)
            .await()
    }

    override suspend fun fetchCurrentCrop(userId: String): Crop? {
        val snapshot = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .whereEqualTo("status", "Active")
            .limit(1)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.toObject(Crop::class.java)
    }

    override suspend fun updateCropField(userId: String, cropId: String, field: String, value: Any) {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(cropId)
            .update(field, value)
            .await()
    }

    override suspend fun fetchCropHistory(
        userId: String,
        cropId: String,
        type: ChartType
    ): List<ChartDataPoint> {
        val valueField = type.toValueField()
        val usageField = type.toUsageField()

        val snapshot = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(cropId)
            .collection("history")
            .orderBy("date")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val dateStr = doc.getString("date")?.trim().orEmpty()
            if (dateStr.isBlank()) return@mapNotNull null

            ChartDataPoint(
                value = doc.getDouble(valueField)?.toFloat() ?: 0f,
                dayLabel = "",
                dataLabel = dateStr,
                usageValue = doc.getDouble(usageField)?.toFloat() ?: 0f,
                hour = null,
                isMissing = false
            )
        }
    }

    override suspend fun fetchCropHourly(
        userId: String,
        cropId: String,
        type: ChartType
    ): List<ChartDataPoint> {
        val valueField = type.toValueField()

        val cutoff = Timestamp(Date(System.currentTimeMillis() - MS_24H))

        val snapshot = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(cropId)
            .collection(HOUR_COLLECTION)
            .whereGreaterThanOrEqualTo("createdAt", cutoff)
            .orderBy("createdAt")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val ts = doc.getTimestamp("createdAt")?.toDate() ?: return@mapNotNull null
            val value = doc.getDouble(valueField)?.toFloat() ?: 0f
            val cal = java.util.Calendar.getInstance().apply { time = ts }
            val hour = cal.get(java.util.Calendar.HOUR_OF_DAY)

            ChartDataPoint(
                value = value,
                dayLabel = "",
                dataLabel = "",
                usageValue = 0f,
                hour = hour,
                isMissing = false
            )
        }
    }

    override suspend fun seedCropMock_2026_02_05_to_02_16(userId: String) {
        mockDataSeeder.seedCropMock_2026_02_05_to_02_16(userId)
    }

    private fun ChartType.toValueField(): String = when (this) {
        ChartType.LIGHT -> "light"
        ChartType.TEMPERATURE -> "temperature"
        ChartType.HUMIDITY -> "humidity"
        ChartType.NUTRITION -> "nutrition"
    }

    private fun ChartType.toUsageField(): String = when (this) {
        ChartType.LIGHT -> "lightUsage"
        ChartType.TEMPERATURE -> "tempUsage"
        ChartType.HUMIDITY -> "waterUsage"
        ChartType.NUTRITION -> "nutritionUsage"
    }
}
