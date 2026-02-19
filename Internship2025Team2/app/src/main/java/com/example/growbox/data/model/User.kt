package com.example.growbox.data.model

data class User(
    val userId: String = "",
    val email: String = "",
    val farmName: String = "",
    val totalHarvestCount: Int = 0,
    val totalDaysGrown: Int = 0
)