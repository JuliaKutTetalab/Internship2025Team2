package com.example.growbox.data

import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.data.model.User
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

interface GrowBoxRepository {

    suspend fun registerAndInitialize(email: String, password: String)

    suspend fun signIn(email: String, password: String)

    suspend fun isLoggedIn(): Boolean

    fun getCurrentUserId(): String?

    fun getCurrentUserEmail(): String?

    suspend fun signOut()

    suspend fun startNewCropCycle(userId: String, cropType: String)

    suspend fun harvestAndStartNewCycle(userId: String, newCropType: String)

    suspend fun markCropHarvested(userId: String, cropId: String)

    suspend fun syncCropDay(userId: String, cropId: String)

    suspend fun fetchCurrentCrop(userId: String): Crop?

    fun getLocalCropStream(userId: String): Flow<Crop?>

    suspend fun getLocalCropOnce(userId: String): Crop?

    fun getAllCrops(userId: String): Flow<List<Crop>>

    suspend fun syncAllCrops(userId: String)

    fun observeCropRealtime(userId: String)

    fun stopObserveCropRealtime()

    suspend fun updateVentStatus(userId: String, cropId: String, isOn: Boolean)
    suspend fun updateVentHours(userId: String, cropId: String, hours: Int)
    suspend fun updateLightStatus(userId: String, cropId: String, isOn: Boolean)
    suspend fun updateLightValue(userId: String, cropId: String, value: Int)
    suspend fun updateCurrentTemperature(userId: String, cropId: String, temp: Int)
    suspend fun updateCurrentHumidity(userId: String, cropId: String, humidity: Int)
    suspend fun updateNutritionValue(userId: String, cropId: String, mg: Int)
    suspend fun updateWateringStatus(userId: String, cropId: String, isOn: Boolean)
    suspend fun updateWateringValue(userId: String, cropId: String, value: Int)

    fun getCropHistory(userId: String, cropId: String, type: ChartType): Flow<List<ChartDataPoint>>

    fun getCropHourly(userId: String, cropId: String, type: ChartType): Flow<List<ChartDataPoint>>
}

class GrowBoxRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val offlineRepository: OfflineRepository,
    private val firestore: FirebaseFirestore
) : GrowBoxRepository {

    private val userStatsUseCase = UserStatsUseCase(offlineRepository, firestore)

    private val cropUseCase = CropUseCase(firebaseDataSource, offlineRepository, userStatsUseCase)

    private val sensorUseCase = SensorUseCase(firebaseDataSource, offlineRepository, firestore)

    private val chartDataUseCase = ChartDataUseCase(firebaseDataSource, offlineRepository, firestore)

    private val cropSyncUseCase = CropSyncUseCase(offlineRepository, firestore, userStatsUseCase)

    override suspend fun registerAndInitialize(email: String, password: String) {
        val userId = firebaseDataSource.registerUser(email, password)
        firebaseDataSource.createUserProfile(userId, email)
        firebaseDataSource.seedCropMock_2026_02_05_to_02_16(userId)
        offlineRepository.insertUser(User(userId = userId, email = email))
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseDataSource.signIn(email, password)
        val uid = getCurrentUserId() ?: return
        syncAllCrops(uid)
        fetchCurrentCrop(uid)
    }

    override suspend fun isLoggedIn(): Boolean =
        firebaseDataSource.isLoggedIn()

    override fun getCurrentUserId(): String? =
        firebaseDataSource.getCurrentUserId()

    override fun getCurrentUserEmail(): String? =
        firebaseDataSource.getCurrentUserEmail()

    override suspend fun signOut() =
        firebaseDataSource.signOut()

    override suspend fun startNewCropCycle(userId: String, cropType: String) =
        cropUseCase.startNewCropCycle(userId, cropType)

    override suspend fun harvestAndStartNewCycle(userId: String, newCropType: String) =
        cropUseCase.harvestAndStartNewCycle(userId, newCropType)

    override suspend fun markCropHarvested(userId: String, cropId: String) =
        cropUseCase.markCropHarvested(userId, cropId)

    override suspend fun syncCropDay(userId: String, cropId: String) =
        cropUseCase.syncCropDay(userId, cropId)

    override suspend fun fetchCurrentCrop(userId: String): Crop? =
        cropSyncUseCase.fetchCurrentCrop(userId)

    override fun getLocalCropStream(userId: String): Flow<Crop?> =
        cropSyncUseCase.getLocalCropStream(userId)

    override suspend fun getLocalCropOnce(userId: String): Crop? =
        cropSyncUseCase.getLocalCropOnce(userId)

    override fun getAllCrops(userId: String): Flow<List<Crop>> =
        cropSyncUseCase.getAllCrops(userId)

    override suspend fun syncAllCrops(userId: String) =
        cropSyncUseCase.syncAllCrops(userId)

    override fun observeCropRealtime(userId: String) =
        cropSyncUseCase.observeCropRealtime(userId)

    override fun stopObserveCropRealtime() =
        cropSyncUseCase.stopObserveCropRealtime()

    override suspend fun updateVentStatus(userId: String, cropId: String, isOn: Boolean) =
        sensorUseCase.updateVentStatus(userId, cropId, isOn)

    override suspend fun updateVentHours(userId: String, cropId: String, hours: Int) =
        sensorUseCase.updateVentHours(userId, cropId, hours)

    override suspend fun updateLightStatus(userId: String, cropId: String, isOn: Boolean) =
        sensorUseCase.updateLightStatus(userId, cropId, isOn)

    override suspend fun updateLightValue(userId: String, cropId: String, value: Int) =
        sensorUseCase.updateLightValue(userId, cropId, value)

    override suspend fun updateCurrentTemperature(userId: String, cropId: String, temp: Int) =
        sensorUseCase.updateCurrentTemperature(userId, cropId, temp)

    override suspend fun updateCurrentHumidity(userId: String, cropId: String, humidity: Int) =
        sensorUseCase.updateCurrentHumidity(userId, cropId, humidity)

    override suspend fun updateNutritionValue(userId: String, cropId: String, mg: Int) =
        sensorUseCase.updateNutritionValue(userId, cropId, mg)

    override suspend fun updateWateringStatus(userId: String, cropId: String, isOn: Boolean) =
        sensorUseCase.updateWateringStatus(userId, cropId, isOn)

    override suspend fun updateWateringValue(userId: String, cropId: String, value: Int) =
        sensorUseCase.updateWateringValue(userId, cropId, value)

    override fun getCropHistory(
        userId: String,
        cropId: String,
        type: ChartType
    ): Flow<List<ChartDataPoint>> =
        chartDataUseCase.getCropHistory(userId, cropId, type)

    override fun getCropHourly(
        userId: String,
        cropId: String,
        type: ChartType
    ): Flow<List<ChartDataPoint>> =
        chartDataUseCase.getCropHourly(userId, cropId, type)
}
