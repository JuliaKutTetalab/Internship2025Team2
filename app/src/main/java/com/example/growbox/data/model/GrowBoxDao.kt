package com.example.growbox.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface GrowBoxDao {


    @Query("""
    SELECT * FROM crops_table
    WHERE ownerId = :userId
    ORDER BY startedAt IS NULL, startedAt DESC
""")
    fun getAllCropsForUser(userId: String): Flow<List<CropEntity>>



    @Query("SELECT * FROM users_table WHERE userId = :id LIMIT 1")
    fun getUserFlow(id: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)




    @Query("""
        SELECT * FROM crops_table
        WHERE ownerId = :userId AND status = 'Active'
        ORDER BY startedAt DESC
        LIMIT 1
    """)
    suspend fun getActiveCropOnce(userId: String): CropEntity?


@Query("""
    SELECT * FROM crops_table
    WHERE ownerId = :userId AND status = 'Active'
    LIMIT 1
""")
fun getCropForUser(userId: String): Flow<CropEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrop(crop: CropEntity)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChartHistory(history: List<ChartHistoryEntity>)


    @Query("""
        SELECT * FROM chart_history_table 
        WHERE cropId = :cropId AND type = :type 
        ORDER BY date ASC
    """)
    fun getChartHistory(cropId: String, type: String): Flow<List<ChartHistoryEntity>>


    @Query("DELETE FROM chart_history_table WHERE cropId = :cropId")
    suspend fun deleteHistoryForCrop(cropId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChartHourly(hourly: List<ChartHourlyEntity>)

    @Query("SELECT * FROM chart_hourly_table WHERE cropId = :cropId AND type = :type AND createdAt >= :since ORDER BY createdAt ASC")
    fun getChartHourly(cropId: String, type: String, since: Long): Flow<List<ChartHourlyEntity>>

    @Query("DELETE FROM chart_hourly_table WHERE cropId = :cropId")
    suspend fun deleteHourlyForCrop(cropId: String)
}
