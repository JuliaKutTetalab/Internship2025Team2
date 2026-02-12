package com.example.growbox.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GrowBoxDao {

    @Query("SELECT * FROM users_table WHERE userId = :id LIMIT 1")
    fun getUserFlow(id: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)


    @Query("SELECT * FROM crops_table WHERE ownerId = :userId LIMIT 1")
    fun getCropForUser(userId: String): Flow<CropEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrop(crop: CropEntity)
}