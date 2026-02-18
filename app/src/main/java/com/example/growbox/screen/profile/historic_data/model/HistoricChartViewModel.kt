package com.example.growbox.screen.profile.historic_data.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod
import com.example.growbox.screen.home.chart.model.ChartType
import com.example.growbox.screen.profile.historic_data.chart_screen.HistoricChartUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class HistoricChartViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId = growBoxRepository.getCurrentUserId().orEmpty()

    private val _chartType: ChartType =
        savedStateHandle.get<String>("chartType")?.let { ChartType.valueOf(it) } ?: ChartType.LIGHT

    val chartType: ChartType get() = _chartType

    private val _uiState = MutableStateFlow(HistoricChartUiState())
    val uiState: StateFlow<HistoricChartUiState> = _uiState.asStateFlow()

    private val DATE_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private var currentJob: Job? = null
    private var cropId: String = ""

    init {
        loadCropAndObserve()
    }

    fun onPeriodSelected(period: ChartPeriod) {
        _uiState.value = _uiState.value.copy(selectedPeriod = period)
        observeForPeriod(period)
    }

    private fun loadCropAndObserve() {
        viewModelScope.launch {
            if (userId.isBlank()) {
                _uiState.value = HistoricChartUiState(isLoading = false)
                return@launch
            }

            val active = offlineRepository.getCropOnce(userId)
            cropId = active?.cropId.orEmpty()

            if (cropId.isBlank()) {
                _uiState.value = HistoricChartUiState(isLoading = false)
                return@launch
            }

            observeForPeriod(_uiState.value.selectedPeriod)
        }
    }

    private fun observeForPeriod(period: ChartPeriod) {
        if (cropId.isBlank()) return

        currentJob?.cancel()

        val unit = when (_chartType) {
            ChartType.LIGHT -> "%"
            ChartType.TEMPERATURE -> "°"
            ChartType.HUMIDITY -> "%"
            ChartType.NUTRITION -> "mg"
        }

        when (period) {
            ChartPeriod.DAY -> observeHourly(unit)
            ChartPeriod.WEEK -> observeHistory(unit, 7)
            ChartPeriod.MONTH -> observeHistory(unit, 30)
        }
    }

    private fun observeHourly(unit: String) {
        currentJob = viewModelScope.launch {
            growBoxRepository.getCropHourly(userId, cropId, _chartType)
                .map { hourlyPoints ->
                    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                    val startHour = (currentHour - 23 + 24) % 24

                    val mapByHour = mutableMapOf<Int, ChartDataPoint>()
                    for (p in hourlyPoints) {
                        val h = p.hour ?: continue
                        mapByHour[h] = p
                    }

                    val data = (0..23).map { idx ->
                        val h = (startHour + idx) % 24
                        val label = String.format("%02d:00", h)
                        val p = mapByHour[h]

                        if (p != null) {
                            p.copy(hour = idx, dayLabel = label, dataLabel = label, isMissing = false)
                        } else {
                            ChartDataPoint(
                                value = 0f, dayLabel = label, dataLabel = label,
                                usageValue = 0f, hour = idx, isMissing = true
                            )
                        }
                    }

                    val nonMissing = data.filter { !it.isMissing }
                    val lowest = nonMissing.minByOrNull { it.value }
                    val highest = nonMissing.maxByOrNull { it.value }

                    HistoricChartUiState(
                        selectedPeriod = ChartPeriod.DAY,
                        data = data,
                        unit = unit,
                        lowestValue = lowest?.value?.toInt() ?: 0,
                        lowestData = lowest?.dayLabel ?: "",
                        highestValue = highest?.value?.toInt() ?: 0,
                        highestData = highest?.dayLabel ?: "",
                        isLoading = false
                    )
                }
                .catch {
                    _uiState.value = HistoricChartUiState(isLoading = false)
                }
                .collect { _uiState.value = it }
        }
    }

    private fun observeHistory(unit: String, days: Int) {
        currentJob = viewModelScope.launch {
            growBoxRepository.getCropHistory(userId, cropId, _chartType)
                .map { full ->
                    val yesterday = LocalDate.now(ZoneId.systemDefault()).minusDays(1)
                    val start = yesterday.minusDays((days - 1).toLong())

                    val byDate = full.mapNotNull { p ->
                        val d = runCatching { LocalDate.parse(p.dataLabel.trim(), DATE_FMT) }.getOrNull()
                        if (d != null) d to p else null
                    }.toMap()

                    val data = (0 until days).map { idx ->
                        val d = start.plusDays(idx.toLong())
                        val fromDb = byDate[d]
                        val dayLabel = when (d.dayOfWeek.value) {
                            1 -> "Mon"; 2 -> "Tue"; 3 -> "Wed"; 4 -> "Thu"
                            5 -> "Fri"; 6 -> "Sat"; else -> "Sun"
                        }

                        if (fromDb != null) {
                            fromDb.copy(
                                dayLabel = dayLabel,
                                dataLabel = d.format(DATE_FMT),
                                isMissing = false
                            )
                        } else {
                            ChartDataPoint(
                                value = 0f, dayLabel = dayLabel,
                                dataLabel = d.format(DATE_FMT),
                                usageValue = 0f, hour = null, isMissing = true
                            )
                        }
                    }

                    val nonMissing = data.filter { !it.isMissing }
                    val lowest = nonMissing.minByOrNull { it.value }
                    val highest = nonMissing.maxByOrNull { it.value }

                    val period = if (days == 7) ChartPeriod.WEEK else ChartPeriod.MONTH

                    HistoricChartUiState(
                        selectedPeriod = period,
                        data = data,
                        unit = unit,
                        lowestValue = lowest?.value?.toInt() ?: 0,
                        lowestData = lowest?.dataLabel ?: "",
                        highestValue = highest?.value?.toInt() ?: 0,
                        highestData = highest?.dataLabel ?: "",
                        isLoading = false
                    )
                }
                .catch {
                    _uiState.value = HistoricChartUiState(isLoading = false)
                }
                .collect { _uiState.value = it }
        }
    }
}
