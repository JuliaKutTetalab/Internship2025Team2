package com.example.growbox.data

import com.example.growbox.data.GrowBoxRepositoryImpl.Companion.CROPS_COLLECTION
import com.example.growbox.data.GrowBoxRepositoryImpl.Companion.USERS_COLLECTION
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
    suspend fun registerUser(email: String, password: String): String // Повертає UID
    // Операції Firestore
    suspend fun createUserProfile(userId: String, email: String)
    suspend fun saveNewCrop(userId: String, crop: Crop)
     suspend fun signIn(email: String, password: String): String


    suspend fun isLoggedIn(): Boolean


}



class FirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseDataSource {



    override suspend fun registerUser(email: String, password: String): String {

        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw IllegalStateException("UID is null")
    }

    override suspend fun createUserProfile(userId: String, email: String) {
        val initialUser =
            User(userId = userId, email = email, farmName = "GrowBox-${userId.take(5)}")

        firestore.collection(USERS_COLLECTION).document(userId).set(initialUser).await()
    }

    override suspend fun saveNewCrop(userId: String, crop: Crop) {

        firestore.collection(USERS_COLLECTION).document(userId).collection(CROPS_COLLECTION).add(crop).await()
    }

    override suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {

        return@withContext auth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String): String {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        if (userId != null) {
                            continuation.resume(userId)
                        } else {
                            continuation.resumeWithException(IllegalStateException("Sign-in failed: User ID is null"))
                        }
                    } else {

                        continuation.resumeWithException(task.exception ?: Exception("Sign-in failed"))
                    }
                }
        }
    }

}