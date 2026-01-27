package com.example.growbox.screen.home.chart.model

import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod

data class ChartUiState(
    val chartType: ChartType = ChartType.LIGHT,
    val currentValue: Int = 0,
    val recommendedValue: Int = 0,
    val weekConsumption: String = "",
    val totalConsumption: String = "",
    val chartData: List<ChartDataPoint> = emptyList(),
    val selectedPeriod: ChartPeriod = ChartPeriod.WEEK,

    val unit: String = "%",
    val iconRes: Int = 0,
    val title: String = "",
    val isLoading: Boolean = false
)