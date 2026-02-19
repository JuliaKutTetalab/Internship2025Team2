package com.example.growbox.screen.profile.my_harvest.model



data class HarvestSummaryItem(
    val cropType: String,
    val harvests: Int,
    val totalDays: Int
)

data class MyHarvestUiState(
    val items: List<HarvestSummaryItem> = emptyList(),
    val isLoading: Boolean = true
)