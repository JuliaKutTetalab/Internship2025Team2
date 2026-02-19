package com.example.growbox.screen.profile.my_harvest


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.screen.profile.my_harvest.model.HarvestSummaryItem

import com.example.growbox.screen.profile.my_harvest.model.MyHarvestUiState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MyHarvestViewModel(
    private val growBoxRepository: GrowBoxRepository
) : ViewModel() {

    private val userId: String = growBoxRepository.getCurrentUserId() ?: ""

    private val _uiState = MutableStateFlow(MyHarvestUiState())
    val uiState: StateFlow<MyHarvestUiState> = _uiState.asStateFlow()

    init {
        if (userId.isBlank()) {
            _uiState.value = MyHarvestUiState(items = emptyList(), isLoading = false)
        } else {
            observeHarvest()
        }
    }

    private fun observeHarvest() {
        viewModelScope.launch {
            growBoxRepository.getAllCrops(userId)
                .map { crops ->
                    crops
                        .filter { it.cropType.isNotBlank() }
                        .groupBy { it.cropType.trim() }
                        .map { (type, list) ->

                            val totalRealDays = list.sumOf { crop ->
                                when {
                                    crop.status.equals("Harvested", ignoreCase = true) -> crop.totalDays
                                    crop.status.equals("Active", ignoreCase = true) -> crop.currentDay
                                    else -> crop.currentDay
                                }
                            }

                            HarvestSummaryItem(
                                cropType = type,
                                harvests = list.size,
                                totalDays = totalRealDays
                            )
                        }
                        .sortedWith(
                            compareByDescending<HarvestSummaryItem> { it.harvests }
                                .thenByDescending { it.totalDays }
                                .thenBy { it.cropType }
                        )
                }
                .catch { e ->
                    Log.e("MY_HARVEST", "getAllCrops error", e)
                    _uiState.value = MyHarvestUiState(items = emptyList(), isLoading = false)
                }
                .collect { items ->
                    _uiState.value = MyHarvestUiState(items = items, isLoading = false)
                }
        }
    }
}
