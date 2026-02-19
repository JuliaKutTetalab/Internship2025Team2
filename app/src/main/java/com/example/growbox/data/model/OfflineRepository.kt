package com.example.growbox.data.model


import com.example.growbox.screen.home.chart.model.ChartDataPoint

import com.example.growbox.data.model.mapper.toCropDomain
import com.example.growbox.data.model.mapper.toCropEntity
import com.example.growbox.data.model.mapper.toChartDomain
import com.example.growbox.data.model.mapper.toChartEntity
import com.example.growbox.data.model.mapper.toHourlyEntity
import com.example.growbox.data.model.mapper.toUserDomain
import com.example.growbox.data.model.mapper.toUserEntity


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map





interface OfflineRepository {
    fun getAllCropsStream(userId: String): Flow<List<Crop>>
    fun getCropStream(userId: String): Flow<Crop?>
    suspend fun insertCrop(crop: Crop, ownerId: String)
    suspend fun insertUser(user: User)
    fun getUserStream(userId: String): Flow<User?>


    fun getChartHistoryStream(cropId: String, type: String): Flow<List<ChartDataPoint>>
    suspend fun insertChartHistory(cropId: String, type: String, history: List<ChartDataPoint>)

    fun getChartHourlyStream(cropId: String, type: String, since: Long): Flow<List<ChartDataPoint>>
    suspend fun insertChartHourly(cropId: String, type: String, hourly: List<ChartDataPoint>, createdAtList: List<Long>)

    suspend fun getCropOnce(userId: String): Crop?

}


class OfflineCropRepository(
    private val growBoxDao: GrowBoxDao
) : OfflineRepository {

    override fun getAllCropsStream(userId: String): Flow<List<Crop>> {
        return growBoxDao.getAllCropsForUser(userId)
            .map { list -> list.map { it.toCropDomain() } }
    }

    override fun getUserStream(userId: String): Flow<User?> {
        return growBoxDao.getUserFlow(userId).map { it?.toUserDomain() }
    }

    override suspend fun insertUser(user: User) {

        growBoxDao.insertUser(user.toUserEntity())
    }


    override fun getCropStream(userId: String): Flow<Crop?> {
        return growBoxDao.getCropForUser(userId)
            .map { it?.toCropDomain() }
            .distinctUntilChanged()
    }


    override suspend fun insertCrop(crop: Crop, ownerId: String) {

        growBoxDao.insertCrop(crop.toCropEntity(ownerId))
    }




    override fun getChartHistoryStream(cropId: String, type: String): Flow<List<ChartDataPoint>> {
        return growBoxDao.getChartHistory(cropId, type).map { list ->
            list.map { it.toChartDomain() }
        }
    }

    override suspend fun insertChartHistory(cropId: String, type: String, history: List<ChartDataPoint>) {
        val entities = history.map { it.toChartEntity(cropId, type) }
        growBoxDao.insertChartHistory(entities)
    }

    override fun getChartHourlyStream(cropId: String, type: String, since: Long): Flow<List<ChartDataPoint>> {
        return growBoxDao.getChartHourly(cropId, type, since).map { list ->
            list.map { it.toChartDomain() }
        }
    }

    override suspend fun insertChartHourly(cropId: String, type: String, hourly: List<ChartDataPoint>, createdAtList: List<Long>) {
        val entities = hourly.mapIndexed { index, point ->
            point.toHourlyEntity(cropId, type, createdAtList.getOrElse(index) { 0L })
        }
        growBoxDao.insertChartHourly(entities)
    }

    override suspend fun getCropOnce(userId: String): Crop? {
        return growBoxDao.getActiveCropOnce(userId)?.toCropDomain()
    }

}