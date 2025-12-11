package com.example.growbox.data.model


data class Crop(
    val cropId: String = "",
    val userId: String = "",
    val cropType: String = "",
    val startDate: Long = 0L,
    val isCurrentCrop: Boolean = false,
    val status: String = "Active"

)