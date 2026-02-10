package com.example.growbox.data.model.mapper

import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.CropEntity
import com.example.growbox.data.model.User
import com.example.growbox.data.model.UserEntity





fun Crop.toEntity(ownerId: String): CropEntity {
    return CropEntity(
        cropId = this.cropId,
        ownerId = ownerId,
        cropType = this.cropType,
        status = this.status,
        currentDay = this.currentDay,
        totalDays = this.totalDays,
        temperature = this.temperature,
        humidity = this.humidity,
        light = this.light,
        nutrition = this.nutrition,
        isVentOn = this.isVentOn,
        isWateringOn = this.isWateringOn
    )
}



fun CropEntity.toDomain(): Crop {
    return Crop(
        cropId = this.cropId,
        userId = this.ownerId, // В CropEntity це ownerId, в Crop це userId
        cropType = this.cropType,
        status = this.status,
        currentDay = this.currentDay,
        totalDays = this.totalDays,
        temperature = this.temperature,
        humidity = this.humidity,
        light = this.light,
        nutrition = this.nutrition,
        isVentOn = this.isVentOn,
        isWateringOn = this.isWateringOn
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        email = this.email,
        farmName = this.farmName,
        totalHarvestCount = this.totalHarvestCount,
        totalDaysGrown = this.totalDaysGrown
    )
}

fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        email = this.email,
        farmName = this.farmName,
        totalHarvestCount = this.totalHarvestCount,
        totalDaysGrown = this.totalDaysGrown
    )
}