package com.example.growbox.data.model.mapper

import com.example.growbox.data.model.ChartHistoryEntity
import com.example.growbox.data.model.ChartHourlyEntity
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.CropEntity
import com.example.growbox.data.model.User
import com.example.growbox.data.model.UserEntity
import com.example.growbox.screen.home.chart.model.ChartDataPoint

import com.google.firebase.Timestamp
import java.util.Date

fun Crop.toCropEntity(ownerId: String): CropEntity = CropEntity(
    cropId = cropId,
    ownerId = ownerId,
    cropType = cropType,
    status = status,
    currentDay = currentDay,
    totalDays = totalDays,

    startedAt = startedAt?.toDate()?.time,
    endsAt = endsAt?.toDate()?.time,

    temperature = temperature,
    humidity = humidity,
    nutrition = nutrition,

    ventHours = ventHours,
    isVentOn = isVentOn,

    isLightOn = isLightOn,
    light = light,

    isWateringOn = isWateringOn,
    watering = watering,

    lightRecommended = lightRecommended,
    tempRecommended = tempRecommended,
    humidityRecommended = humidityRecommended,
    nutritionRecommended = nutritionRecommended
)



fun CropEntity.toCropDomain(): Crop = Crop(
    cropId = cropId,
    userId = ownerId,
    cropType = cropType,
    status = status,
    currentDay = currentDay,
    totalDays = totalDays,
    startedAt = startedAt?.let { Timestamp(Date(it)) },
    endsAt = endsAt?.let { Timestamp(Date(it)) },

    temperature = temperature,
    humidity = humidity,
    light = light,
    nutrition = nutrition,

    isVentOn = isVentOn,
    isWateringOn = isWateringOn,
    ventHours = ventHours,
    isLightOn = isLightOn,
    watering = watering,

    lightRecommended = lightRecommended,
    tempRecommended = tempRecommended,
    humidityRecommended = humidityRecommended,
    nutritionRecommended = nutritionRecommended
)


fun ChartHistoryEntity.toChartDomain(): ChartDataPoint {
    return ChartDataPoint(
        value = this.value,
        dayLabel = this.dayLabel,
        dataLabel = this.date,
        usageValue = this.usageValue
    )
}

fun ChartDataPoint.toChartEntity(cropId: String, type: String): ChartHistoryEntity {
    return ChartHistoryEntity(
        cropId = cropId,
        type = type,
        value = this.value,
        dayLabel = this.dayLabel,
        date = this.dataLabel,
        usageValue = this.usageValue
    )
}

fun ChartHourlyEntity.toChartDomain(): ChartDataPoint {
    return ChartDataPoint(
        value = this.value,
        dayLabel = "",
        dataLabel = "",
        usageValue = 0f,
        hour = this.hour,
        isMissing = false
    )
}

fun ChartDataPoint.toHourlyEntity(cropId: String, type: String, createdAt: Long): ChartHourlyEntity {
    return ChartHourlyEntity(
        cropId = cropId,
        type = type,
        hour = this.hour ?: 0,
        value = this.value,
        createdAt = createdAt
    )
}


fun UserEntity.toUserDomain(): User {
    return User(
        userId = this.userId,
        email = this.email,
        farmName = this.farmName,
        totalHarvestCount = this.totalHarvestCount,
        totalDaysGrown = this.totalDaysGrown
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        email = this.email,
        farmName = this.farmName,
        totalHarvestCount = this.totalHarvestCount,
        totalDaysGrown = this.totalDaysGrown
    )
}
