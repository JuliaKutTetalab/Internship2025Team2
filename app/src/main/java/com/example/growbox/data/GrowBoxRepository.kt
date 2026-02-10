package com.example.growbox.data

import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.data.model.User
import java.util.UUID

interface GrowBoxRepository {

    suspend fun registerAndInitialize(email: String, password: String)


    suspend fun startNewCropCycle(userId: String, cropType: String)

    suspend fun signIn(email: String, password: String)

    suspend  fun isLoggedIn(): Boolean

    fun getCurrentUserId(): String?
    suspend fun fetchCurrentCrop(userId: String): Crop?

    suspend fun updateVentStatus(userId: String, cropId: String, isActive: Boolean)

    suspend fun updateWateringStatus(userId: String, cropId: String, isActive: Boolean)

    /** Отримати Email поточного користувача */
    fun getCurrentUserEmail(): String?

    /** Вийти з облікового запису */
    suspend fun signOut()
}


class GrowBoxRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val offlineRepository: OfflineRepository // Додано в конструктор
) : GrowBoxRepository {

    override fun getCurrentUserEmail(): String? = firebaseDataSource.getCurrentUserEmail()

    override suspend fun signOut() {
        firebaseDataSource.signOut()
    }

    override suspend fun registerAndInitialize(email: String, password: String) {
        val userId = firebaseDataSource.registerUser(email, password)
        firebaseDataSource.createUserProfile(userId, email)


        val newUser = User(userId = userId, email = email)
        offlineRepository.insertUser(newUser)
    }

    override suspend fun startNewCropCycle(userId: String, cropType: String) {
        val newCropId = UUID.randomUUID().toString()

        val newCrop = Crop(
            cropId = newCropId,
            userId = userId,
            cropType = cropType,
            status = "Active",
            currentDay = 1,
            totalDays = 21,
            temperature = 22,
            humidity = 50,
            light = 0,
            nutrition = 0,
            isVentOn = false,
            isWateringOn = false
        )

        // 1. У хмару
        firebaseDataSource.saveNewCrop(userId, newCrop)
        // 2. У локальну базу (Room)
        offlineRepository.insertCrop(newCrop, userId)
    }

    override suspend fun fetchCurrentCrop(userId: String): Crop? {
        val remoteCrop = firebaseDataSource.fetchCurrentCrop(userId)

        if (remoteCrop != null) {
            offlineRepository.insertCrop(remoteCrop, userId)
        }
        return remoteCrop
    }

    override suspend fun updateVentStatus(userId: String, cropId: String, isActive: Boolean) {
        try {

            firebaseDataSource.updateCropField(userId, cropId, "isVentOn", isActive)

            fetchCurrentCrop(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateWateringStatus(userId: String, cropId: String, isActive: Boolean) {
        try {
            firebaseDataSource.updateCropField(userId, cropId, "isWateringOn", isActive)
            fetchCurrentCrop(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseDataSource.signIn(email, password)
        getCurrentUserId()?.let { fetchCurrentCrop(it) }
    }

    override suspend fun isLoggedIn(): Boolean = firebaseDataSource.isLoggedIn()
    override fun getCurrentUserId(): String? = firebaseDataSource.getCurrentUserId()
}



