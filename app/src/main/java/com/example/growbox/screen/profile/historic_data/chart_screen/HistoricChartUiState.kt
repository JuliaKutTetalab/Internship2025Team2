package com.example.growbox.screen.profile.historic_data.chart_screen

import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod


data class HistoricChartUiState(
    val selectedPeriod: ChartPeriod = ChartPeriod.WEEK,
    val data: List<ChartDataPoint> = emptyList(),

    val lowestValue: Int = 0,
    val lowestData: String = "",
    val highestValue: Int = 0,
    val highestData: String = "",

    val unit: String = "",
    val isLoading: Boolean = true
)