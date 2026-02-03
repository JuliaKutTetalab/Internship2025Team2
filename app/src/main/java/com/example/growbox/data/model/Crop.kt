package com.example.growbox.data.model




data class Crop(
    val cropId: String = "",
    val userId: String = "",
    val cropType: String = "",
    val status: String = "Active",
    val currentDay: Int = 1,
    val totalDays: Int = 21,
    val temperature: Int = 0,
    val humidity: Int = 0,
    val light: Int = 0,
    val nutrition: Int = 0,
    val isVentOn: Boolean = false,
    val isWateringOn: Boolean = false
)