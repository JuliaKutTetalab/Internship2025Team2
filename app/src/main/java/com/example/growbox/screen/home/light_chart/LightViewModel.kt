package com.example.growbox.screen.home.light_chart

import androidx.lifecycle.ViewModel
import com.example.growbox.screen.home.light_chart.model.ChartDataPoint
import com.example.growbox.screen.home.light_chart.model.LightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.growbox.screen.home.light_chart.model.ChartPeriod

class LightViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LightUiState())
    val uiState: StateFlow<LightUiState> = _uiState.asStateFlow()

    fun onPeriodSelected(period: ChartPeriod){
        _uiState.value = _uiState.value.copy(
            selectedPeriod = period
        )
        loadDataForPeriod(period)

    }

    private fun loadDataForPeriod(period: ChartPeriod){
        //TODO: Різні дані для різних періодів(треба зробити!)

        val testChartData = mutableListOf<ChartDataPoint>()

        when (period){
            ChartPeriod.DAY -> {
                //for day (hours)
                val hours = listOf("00", "04", "08", "12", "16", "20", "23")
                for (i in hours.indices){
                    testChartData.add(
                        ChartDataPoint(
                            value = (20..100).random().toFloat(),
                            dayLabel = hours[i],
                            dataLabel = "h"
                        )
                    )
                }
            }

            ChartPeriod.WEEK -> {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val dates = listOf("08", "09", "10", "11", "12", "13", "14")

                for (i in days.indices){
                    testChartData.add(
                        ChartDataPoint(
                            value = (20..100).random().toFloat(),
                            dayLabel = days[i],
                            dataLabel = dates[i]
                        )
                    )
                }
            }

            ChartPeriod.MONTH -> {
                //for month (12 months)
                val months = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
                for (i in months.indices){
                    testChartData.add(
                        ChartDataPoint(
                            value = (30..90).random().toFloat(),
                            dayLabel = months[i],
                            dataLabel = ""
                        )
                    )
                }
            }
        }

        _uiState.value = _uiState.value.copy(
            chartData = testChartData
        )
    }

    fun loadData() {
        //TODO: поки тестові дані, потім підключимо API
        val testChartData = mutableListOf<ChartDataPoint>()
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val dates = listOf("08", "09", "10", "11", "12", "13", "14")

        for (i in 0 until 7){
            testChartData.add(
                ChartDataPoint(
                    value = (20..100).random().toFloat(),
                    dayLabel = days[i],
                    dataLabel = dates[i]
                )
            )
        }

        _uiState.value = LightUiState(
            currentValue = 60,
            recommendedValue = 72,
            weekConsumption = "60 ",
            totalConsumption = "456 ",
            chartData = testChartData,
            selectedPeriod = ChartPeriod.WEEK
        )
    }
}