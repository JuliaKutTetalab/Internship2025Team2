package com.example.growbox.screen.home.chart.model

data class ChartDataPoint(
    val value: Float = 0f,
    val dayLabel: String = "",
    val dataLabel: String = "",
    val usageValue: Float = 0f,
    val hour: Int? = null,
    val isMissing: Boolean = false
)
