package com.example.growbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey val userId: String,
    val email: String,
    val farmName: String,
    val totalHarvestCount: Int,
    val totalDaysGrown: Int
)