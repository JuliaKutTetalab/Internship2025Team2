package com.example.growbox.screen.home.light_chart.model

data class LightUiState(
    val currentValue: Int = 0,
    val recommendedValue: Int = 0,
    val weekConsumption: String = "0 kw",
    val totalConsumption: String = "456 kw",

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