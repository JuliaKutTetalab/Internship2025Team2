package com.example.growbox.screen.home.chart

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.growbox.R
import com.example.growbox.screen.home.chart.model.ChartType
import com.example.growbox.screen.home.chart.model.ChartUiState
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChartViewModel(
//    savedStateHandle: SavedStateHandle
    private val chartType: ChartType
): ViewModel() {

    // Тип отримуємо з навігації
//    private val chartType: ChartType = savedStateHandle.get<String>("chartType")?.let {
//        ChartType.valueOf(it)
//    } ?: ChartType.LIGHT

    private val _uiState = MutableStateFlow(ChartUiState())
    val uiState: StateFlow<ChartUiState> = _uiState.asStateFlow()

    init {
        loadDataForType(chartType)
    }

    private fun loadDataForType(type: ChartType){
        when (type){
            ChartType.LIGHT -> loadLightData()
            ChartType.TEMPERATURE -> loadTemperatureData()
            ChartType.HUMIDITY -> loadHumidityData()
            ChartType.NUTRITION -> loadNutritionData()
        }
    }

    private fun loadLightData() {
        val testChartData = generateChartData(20..80)

        _uiState.value = ChartUiState(
            chartType = ChartType.LIGHT,
            currentValue = 60,
            recommendedValue = 72,
            weekConsumption = "60 ",
            totalConsumption = "456 ",
            chartData = testChartData,
            unit = "%",
            currentUnit = "%",
            consumptionUnit = "kw",
            iconRes = R.drawable.ic_light_icon,
            titleRes = R.string.light_title
        )
    }
    private fun loadTemperatureData() {
        val testChartData = generateChartData(10..36)

        _uiState.value = ChartUiState(
            chartType = ChartType.TEMPERATURE,
            currentValue = 24,
            recommendedValue = 26,
            weekConsumption = "24 ",
            totalConsumption = "26 ",
            chartData = testChartData,
            unit = "`C",
            currentUnit = "`C`",
            consumptionUnit = "kw",
            iconRes = R.drawable.ic_temperature_icon,
            titleRes = R.string.temperature_title
        )
    }
    private fun loadHumidityData() {
        val testChartData = generateChartData(40..70)

        _uiState.value = ChartUiState(
            chartType = ChartType.HUMIDITY,
            currentValue = 55,
            recommendedValue = 60,
            weekConsumption = "55 ",
            totalConsumption = "70 ",
            chartData = testChartData,
            unit = "%",
            currentUnit = "%",
            consumptionUnit = "ml",
            iconRes = R.drawable.ic_humidity_icon,
            titleRes = R.string.humidity_title
        )
    }
    private fun loadNutritionData() {
        val testChartData = generateChartData(10..30)

        _uiState.value = ChartUiState(
            chartType = ChartType.NUTRITION,
            currentValue = 250,
            recommendedValue = 300,
            weekConsumption = "250 ",
            totalConsumption = "500 ",
            chartData = testChartData,
            unit = "mg",
            currentUnit = "%",
            consumptionUnit = "mg",
            iconRes = R.drawable.ic_nutrition_icon,
            titleRes = R.string.nutrition_title
        )
    }

    private fun generateChartData(range: IntRange): List<ChartDataPoint>{
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val dates = listOf("08", "09", "10", "11", "12", "13", "14")

        return days.mapIndexed { index, day ->
            ChartDataPoint(
                value = range.random().toFloat(),
                dayLabel = day,
                dataLabel = dates[index]
            )
        }
    }
    private fun loadDataForPeriod(period: ChartPeriod) {
        val testChartData = mutableListOf<ChartDataPoint>()
        val range = when (chartType){
            ChartType.LIGHT -> 20..80
            ChartType.HUMIDITY -> 40..70
            ChartType.NUTRITION -> 10..30
            ChartType.TEMPERATURE -> 10..36
        }

        when (period) {
            ChartPeriod.DAY -> {
                //for day (hours)
                val hours = listOf("00", "04", "08", "12", "16", "20", "23")
                for (i in hours.indices) {
                    testChartData.add(
                        ChartDataPoint(
                            value = range.random().toFloat(),
                            dayLabel = hours[i],
                            dataLabel = "h"
                        )
                    )
                }
            }

            ChartPeriod.WEEK -> {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val dates = listOf("08", "09", "10", "11", "12", "13", "14")

                for (i in days.indices) {
                    testChartData.add(
                        ChartDataPoint(
                            value = range.random().toFloat(),
                            dayLabel = days[i],
                            dataLabel = dates[i]
                        )
                    )
                }
            }

            ChartPeriod.MONTH -> {
                //for month (12 months)
                val months =
                    listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
                for (i in months.indices) {
                    testChartData.add(
                        ChartDataPoint(
                            value = range.random().toFloat(),
                            dayLabel = months[i],
                            dataLabel = ""
                        )
                    )
                }
            }
        }
        _uiState.value = _uiState.value.copy(chartData = testChartData)
    }

    fun onPeriodSelected(period: ChartPeriod){
        _uiState.value = _uiState.value.copy(selectedPeriod = period)
        loadDataForPeriod(period)
    }
}