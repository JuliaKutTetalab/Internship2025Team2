package com.example.growbox.data.model

import com.example.growbox.data.model.mapper.toDomain
import com.example.growbox.data.model.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface OfflineRepository {
    fun getCropStream(userId: String): Flow<Crop?>
    suspend fun insertCrop(crop: Crop, ownerId: String)
    suspend fun insertUser(user: User)

    fun getUserStream(userId: String): Flow<User?>
}

class OfflineCropRepository(
    private val growBoxDao: GrowBoxDao
) : OfflineRepository {

    override fun getUserStream(userId: String): Flow<User?> {
        return growBoxDao.getUserFlow(userId).map { it?.toDomain() }
    }

    override fun getCropStream(userId: String): Flow<Crop?> {
        return growBoxDao.getCropForUser(userId).map { it?.toDomain() }
    }

    override suspend fun insertCrop(crop: Crop, ownerId: String) {
        growBoxDao.insertCrop(crop.toEntity(ownerId))
    }

    override suspend fun insertUser(user: User) {
        growBoxDao.insertUser(user.toEntity())
    }
}