package com.example.growbox.screen.profile.historic_data.chart_screen

data class HistoricChartUiState (
    //Поки нема
//    val selectedPeriod: ChartPeriod = ChartPeriod.WEEK,
//    val chartData: List<ChartDataPoint> = emptyList(),
    val lowestValue: Int = 0,
    val lowestData: String = "",
    val highestValue: Int = 0,
    val highestData: String = "",
    val isLoading: Boolean = false
)