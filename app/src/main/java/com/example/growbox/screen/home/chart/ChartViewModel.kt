package com.example.growbox.screen.home.chart

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.R
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod
import com.example.growbox.screen.home.chart.model.ChartType
import com.example.growbox.screen.home.chart.model.ChartUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ChartViewModel(
    savedStateHandle: SavedStateHandle,
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
) : ViewModel() {

    private var cropStartedAtMillis: Long? = null
    private var cropCurrentDay: Int? = null

    private val cropId: String = savedStateHandle[ChartDestination.cropIdArg] ?: ""
    private val initialType: String = savedStateHandle[ChartDestination.chartTypeArg] ?: "LIGHT"

    private val chartType: ChartType = runCatching { ChartType.valueOf(initialType) }
        .getOrElse { ChartType.LIGHT }

    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""

    private val _uiState = MutableStateFlow(ChartUiState(chartType = chartType))
    val uiState: StateFlow<ChartUiState> = _uiState.asStateFlow()

    private var allHistory: List<ChartDataPoint> = emptyList()
    private var allHourly: List<ChartDataPoint> = emptyList()

    private var historyJob: Job? = null
    private var hourlyJob: Job? = null
    private var cropJob: Job? = null

    private val DATE_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private fun String.toLocalDateOrNull(): LocalDate? =
        runCatching { LocalDate.parse(this.trim(), DATE_FMT) }.getOrNull()

    private fun LocalDate.toIsoString(): String = format(DATE_FMT)

    private fun dayOfWeekShort(date: LocalDate): String = when (date.dayOfWeek.value) {
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thu"
        5 -> "Fri"
        6 -> "Sat"
        else -> "Sun"
    }

    init {
        if (cropId.isNotEmpty()) {
            setupInitialConfiguration()
            observeCropData()
            observeHistory()

            if (_uiState.value.selectedPeriod == ChartPeriod.DAY) observeHourly()

            applyPeriodToUi()
        }
    }

    fun onPeriodSelected(period: ChartPeriod) {
        _uiState.update { it.copy(selectedPeriod = period) }

        if (period == ChartPeriod.DAY) observeHourly() else hourlyJob?.cancel()

        applyPeriodToUi()
    }

    private fun observeCropData() {
        if (currentUserId.isEmpty()) return

        cropJob?.cancel()
        cropJob = viewModelScope.launch {
            offlineRepository.getCropStream(currentUserId).collect { crop ->
                crop ?: return@collect

                cropStartedAtMillis = crop.startedAt?.toDate()?.time
                cropCurrentDay = crop.currentDay

                val value = when (chartType) {
                    ChartType.LIGHT -> crop.light
                    ChartType.TEMPERATURE -> crop.temperature
                    ChartType.HUMIDITY -> crop.humidity
                    ChartType.NUTRITION -> crop.nutrition
                }

                val recommended = when (chartType) {
                    ChartType.LIGHT -> crop.lightRecommended
                    ChartType.TEMPERATURE -> crop.tempRecommended
                    ChartType.HUMIDITY -> crop.humidityRecommended
                    ChartType.NUTRITION -> crop.nutritionRecommended
                }

                _uiState.update { it.copy(currentValue = value, recommendedValue = recommended) }

                applyPeriodToUi()
            }
        }
    }

    private fun observeHistory() {
        if (currentUserId.isEmpty()) return

        historyJob?.cancel()
        historyJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            growBoxRepository.getCropHistory(currentUserId, cropId, chartType)
                .collect { points ->
                    allHistory = points.sortedBy { it.dataLabel.trim() }
                    applyPeriodToUi()
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun observeHourly() {
        if (currentUserId.isEmpty()) return

        hourlyJob?.cancel()

        hourlyJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            growBoxRepository.getCropHourly(currentUserId, cropId, chartType)
                .collect { points ->
                    allHourly = points
                    applyPeriodToUi()
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun applyPeriodToUi() {
        val period = _uiState.value.selectedPeriod

        val window = when (period) {
            ChartPeriod.DAY -> buildDayWindow(allHourly)
            ChartPeriod.WEEK -> buildWeekWindow(allHistory)
            ChartPeriod.MONTH -> buildMonthWindow(allHistory)
        }

        val usageUnit = when (chartType) {
            ChartType.LIGHT -> "kw"
            ChartType.TEMPERATURE -> "kw"
            ChartType.HUMIDITY -> "ml"
            ChartType.NUTRITION -> "mg"
        }

        val periodSum = window.sumOf { it.usageValue.toDouble() }.toFloat()
        val totalSum = allHistory.sumOf { it.usageValue.toDouble() }.toFloat()

        _uiState.update {
            it.copy(
                chartData = window,
                weekConsumption = "${periodSum.toInt()} $usageUnit",
                totalConsumption = "${totalSum.toInt()} $usageUnit",
                weekValue = "${periodSum.toInt()} $usageUnit",
                totalValue = "${totalSum.toInt()} $usageUnit"
            )
        }
    }

    private fun buildDayWindow(hourly: List<ChartDataPoint>): List<ChartDataPoint> {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val startHour = (currentHour - 23 + 24) % 24

        val mapByHour = mutableMapOf<Int, ChartDataPoint>()
        for (p in hourly) {
            val h = p.hour ?: continue
            mapByHour[h] = p
        }

        return (0..23).map { idx ->
            val h = (startHour + idx) % 24
            val label = String.format("%02d:00", h)
            val p = mapByHour[h]

            if (p != null) {
                p.copy(
                    hour = idx,
                    dayLabel = label,
                    dataLabel = "",
                    isMissing = false
                )
            } else {
                ChartDataPoint(
                    value = 0f,
                    dayLabel = label,
                    dataLabel = "",
                    usageValue = 0f,
                    hour = idx,
                    isMissing = true
                )
            }
        }
    }

    private fun buildWeekWindow(history: List<ChartDataPoint>): List<ChartDataPoint> {
        val yesterday = LocalDate.now(ZoneId.systemDefault()).minusDays(1)
        val start = yesterday.minusDays(6)

        val byDate = history.mapNotNull { p ->
            val d = p.dataLabel.toLocalDateOrNull() ?: return@mapNotNull null
            d to p
        }.toMap()

        return (0..6).map { offset ->
            val d = start.plusDays(offset.toLong())
            val fromDb = byDate[d]

            val base = fromDb ?: ChartDataPoint(
                value = 0f,
                dayLabel = "",
                dataLabel = d.toIsoString(),
                usageValue = 0f,
                hour = null,
                isMissing = true
            )

            base.copy(
                dayLabel = dayOfWeekShort(d),
                dataLabel = d.toIsoString(),
                isMissing = fromDb == null
            )
        }
    }

    private fun buildMonthWindow(history: List<ChartDataPoint>): List<ChartDataPoint> {
        val yesterday = LocalDate.now(ZoneId.systemDefault()).minusDays(1)
        val start = yesterday.minusDays(29)

        val byDate = history.mapNotNull { p ->
            val d = p.dataLabel.toLocalDateOrNull() ?: return@mapNotNull null
            d to p
        }.toMap()

        val showPos = setOf(0, 4, 9, 14, 19, 24, 29)

        return (0..29).map { idx ->
            val d = start.plusDays(idx.toLong())
            val fromDb = byDate[d]

            val base = fromDb ?: ChartDataPoint(
                value = 0f,
                dayLabel = "",
                dataLabel = d.toIsoString(),
                usageValue = 0f,
                hour = null,
                isMissing = true
            )

            base.copy(
                dayLabel = if (idx in showPos) d.dayOfMonth.toString() else "",
                dataLabel = d.toIsoString(),
                isMissing = fromDb == null
            )
        }
    }

    private fun setupInitialConfiguration() {
        _uiState.update { state ->
            when (chartType) {
                ChartType.LIGHT -> state.copy(
                    unit = "%",
                    iconRes = R.drawable.ic_light_icon,
                    titleRes = R.string.light_title,
                    consumptionUnit = "kw"
                )
                ChartType.TEMPERATURE -> state.copy(
                    unit = "°C",
                    iconRes = R.drawable.ic_temperature_icon,
                    titleRes = R.string.temperature_title,
                    consumptionUnit = "kw"
                )
                ChartType.HUMIDITY -> state.copy(
                    unit = "%",
                    iconRes = R.drawable.ic_humidity_icon,
                    titleRes = R.string.humidity_title,
                    consumptionUnit = "ml"
                )
                ChartType.NUTRITION -> state.copy(
                    unit = "%",
                    iconRes = R.drawable.ic_nutrition_icon,
                    titleRes = R.string.nutrition_title,
                    consumptionUnit = "mg"
                )
            }
        }
    }

    override fun onCleared() {
        historyJob?.cancel()
        hourlyJob?.cancel()
        cropJob?.cancel()
        super.onCleared()
    }
}
