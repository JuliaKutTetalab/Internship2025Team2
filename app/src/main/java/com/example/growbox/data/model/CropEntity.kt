package com.example.growbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crops_table")
data class CropEntity(
    @PrimaryKey val cropId: String,
    val ownerId: String,
    val cropType: String,
    val status: String,
    val currentDay: Int,
    val totalDays: Int,
    val temperature: Int,
    val humidity: Int,
    val light: Int,
    val nutrition: Int,
    val isVentOn: Boolean,
    val isWateringOn: Boolean
)
