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
    val nutrition: Int,

    val startedAt: Long? = null,
    val endsAt: Long? = null,



    val ventHours: Int = 0,
    val isVentOn: Boolean,

    val isLightOn: Boolean = false,
    val light: Int,

    val isWateringOn: Boolean,
    val watering: Int = 0,


    val lightRecommended: Int = 72,
    val tempRecommended: Int = 26,
    val humidityRecommended: Int = 74,
    val nutritionRecommended: Int = 65
)
@Entity(
    tableName = "chart_history_table",
    primaryKeys = ["cropId", "date", "type"]
)


data class ChartHistoryEntity(
    val cropId: String,
    val type: String,
    val value: Float,
    val dayLabel: String,
    val date: String,


    val usageValue: Float = 0f
)

@Entity(
    tableName = "chart_hourly_table",
    primaryKeys = ["cropId", "type", "hour", "createdAt"]
)
data class ChartHourlyEntity(
    val cropId: String,
    val type: String,
    val hour: Int,
    val value: Float,
    val createdAt: Long
)