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
    savedStateHandle: SavedStateHandle
): ViewModel() {

    // Тип отримуємо з навігації
    private val chartType: ChartType = savedStateHandle.get<String>("chartType")?.let {
        ChartType.valueOf(it)
    } ?: ChartType.LIGHT

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
            weekConsumption = "60 kw",
            totalConsumption = "456 kw",
            chartData = testChartData,
            unit = "%",
            iconRes = R.drawable.ic_light_icon,
            title = R.string.light_title.toString()
        )
    }
    private fun loadTemperatureData() {
        val testChartData = generateChartData(10..36)

        _uiState.value = ChartUiState(
            chartType = ChartType.TEMPERATURE,
            currentValue = 24,
            recommendedValue = 26,
            weekConsumption = "24 `C",
            totalConsumption = "26 `C",
            chartData = testChartData,
            unit = "`C",
            iconRes = R.drawable.ic_temperature_icon,
            title = R.string.temperature_title.toString()
        )
    }
    private fun loadHumidityData() {
        val testChartData = generateChartData(40..70)

        _uiState.value = ChartUiState(
            chartType = ChartType.HUMIDITY,
            currentValue = 55,
            recommendedValue = 60,
            weekConsumption = "55%",
            totalConsumption = "70%",
            chartData = testChartData,
            unit = "%",
            iconRes = R.drawable.ic_humidity_icon,
            title = R.string.humidity_title.toString()
        )
    }
    private fun loadNutritionData() {
        val testChartData = generateChartData(10..36)

        _uiState.value = ChartUiState(
            chartType = ChartType.NUTRITION,
            currentValue = 250,
            recommendedValue = 300,
            weekConsumption = "250 mg",
            totalConsumption = "500 mg",
            chartData = testChartData,
            unit = "mg",
            iconRes = R.drawable.ic_nutrition_icon,
            title = R.string.nutrition_title.toString()
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

    fun onPeriodSelected(period: ChartPeriod){
        _uiState.value = _uiState.value.copy(selectedPeriod = period)
        loadDataForType(chartType)
    }
}