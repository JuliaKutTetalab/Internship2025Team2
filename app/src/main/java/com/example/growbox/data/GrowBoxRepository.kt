package com.example.growbox.data

import com.example.growbox.data.model.Crop

interface GrowBoxRepository {

    suspend fun registerAndInitialize(email: String, password: String)


    suspend fun startNewCropCycle(userId: String, cropType: String)

    suspend fun signIn(email: String, password: String)

    suspend  fun isLoggedIn(): Boolean
}



class GrowBoxRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : GrowBoxRepository {


    companion object {
        const val USERS_COLLECTION = "users"
        const val CROPS_COLLECTION = "crops"
    }

    override suspend fun registerAndInitialize(email: String, password: String) {
        val userId = firebaseDataSource.registerUser(email, password)
        firebaseDataSource.createUserProfile(userId, email)
    }


    override suspend fun startNewCropCycle(userId: String, cropType: String) {
        val newCrop = Crop(
            userId = userId,
            cropType = cropType,
            startDate = System.currentTimeMillis(),
            isCurrentCrop = true,
            status = "Active"
        )
        firebaseDataSource.saveNewCrop(userId, newCrop)
    }

    override suspend fun signIn(email: String, password: String) {

        firebaseDataSource.signIn(email, password)
    }

    override suspend fun isLoggedIn(): Boolean {
        return firebaseDataSource.isLoggedIn()
    }
}