package com.example.growbox.screen.profile.change_crop_type.model

data class ChangeCropUiState(
    val cropType: String = "",
    val currentDay: Int = 0,
    val totalDays: Int = 21,
    val daysLeft: Int = 21,
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)