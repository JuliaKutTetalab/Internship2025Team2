package com.example.growbox.data


import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine



interface FirebaseDataSource {
    // Операції Auth
    suspend fun registerUser(email: String, password: String): String
    suspend fun signIn(email: String, password: String): String
    suspend fun isLoggedIn(): Boolean
    fun getCurrentUserId(): String?

    // Операції Firestore
    suspend fun createUserProfile(userId: String, email: String)
    suspend fun saveNewCrop(userId: String, crop: Crop)
    suspend fun fetchCurrentCrop(userId: String): Crop?
    // Метод для оновлення окремих полів (вентиляція, полив)
    suspend fun updateCropField(userId: String, cropId: String, field: String, value: Any)
}

class FirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseDataSource {

    // Тепер компаньйон на місці, щоб репозиторій міг його використовувати
    companion object {
        const val USERS_COLLECTION = "users"
        const val CROPS_COLLECTION = "crops"
    }

    override suspend fun registerUser(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw IllegalStateException("UID is null")
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
        // ВИПРАВЛЕНО: використовуємо cropId як назву документа, щоб потім його знайти
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(crop.cropId)
            .set(crop)
            .await()
    }

    override suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        return@withContext auth.currentUser != null
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid

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
        // Оновлюємо тільки конкретне поле (наприклад "isVentOn")
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CROPS_COLLECTION)
            .document(cropId)
            .update(field, value)
            .await()
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
}