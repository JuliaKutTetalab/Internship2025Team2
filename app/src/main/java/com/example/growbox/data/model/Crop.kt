package com.example.growbox.data.model


import com.google.firebase.Timestamp

data class Crop(
    val cropId: String = "",
    val userId: String = "",
    val cropType: String = "",
    val status: String = "Active",
    val currentDay: Int = 1,
    val totalDays: Int = 21,

    val startedAt: Timestamp? = null,
    val endsAt: Timestamp? = null,

    val temperature: Int = 0,
    val humidity: Int = 0,
    val light: Int = 0,
    val nutrition: Int = 0,

    @get:com.google.firebase.firestore.PropertyName("isVentOn")
    @set:com.google.firebase.firestore.PropertyName("isVentOn")
    var isVentOn: Boolean = false,

    @get:com.google.firebase.firestore.PropertyName("isWateringOn")
    @set:com.google.firebase.firestore.PropertyName("isWateringOn")
    var isWateringOn: Boolean = false,

    val ventHours: Int = 0,

    @get:com.google.firebase.firestore.PropertyName("isLightOn")
    @set:com.google.firebase.firestore.PropertyName("isLightOn")
    var isLightOn: Boolean = false,

    val watering: Int = 0,

    val lightRecommended: Int = 72,
    val tempRecommended: Int = 26,
    val humidityRecommended: Int = 74,
    val nutritionRecommended: Int = 65
)

