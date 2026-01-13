package com.example.growbox.screen.home.humidity_chart.model

data class HumidityUiState(
    val currentValue: Int = 0,
    val recommendedValue: Int = 0,
    val weekConsumption: String = "0 ml",
    val totalConsumption: String = "456 ml",

    val selectedPeriod: ChartPeriod = ChartPeriod.WEEK,
    val chartData: List<ChartDataPoint> = emptyList(),

    val isLoading: Boolean = false

)

enum class ChartPeriod {
    DAY, WEEK, MONTH
}

data class ChartDataPoint(
    val value: Float,
    val dayLabel: String, //Mon, Tue, Wed, ...
    val dataLabel: String
)