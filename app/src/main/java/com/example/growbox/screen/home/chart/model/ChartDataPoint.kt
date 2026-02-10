package com.example.growbox.screen.home.chart.model

data class ChartDataPoint(
    val value: Float,
    val dayLabel: String, //Mon, Tue, Wed, ...
    val dataLabel: String
)