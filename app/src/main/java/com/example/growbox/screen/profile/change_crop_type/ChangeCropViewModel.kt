package com.example.growbox.screen.profile.change_crop_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.screen.profile.change_crop_type.model.ChangeCropUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.growbox.utils.calculateCurrentDay







class ChangeCropViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
): ViewModel() {
    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""

    private val _uiState = MutableStateFlow(ChangeCropUiState())
    val uiState: StateFlow<ChangeCropUiState> = _uiState.asStateFlow()


    fun loadData() {
        viewModelScope.launch {


            runCatching {
                growBoxRepository.fetchCurrentCrop(currentUserId)
            }


            offlineRepository.getCropStream(currentUserId).collect { crop ->
                crop ?: return@collect

                val computedDay = calculateCurrentDay(crop.startedAt, crop.totalDays)

                val uiDay = when {
                    crop.currentDay > 1 -> crop.currentDay
                    else -> computedDay
                }.coerceIn(1, crop.totalDays)

                val daysLeft = (crop.totalDays - uiDay).coerceAtLeast(0)
                val isHarvestReady = uiDay >= crop.totalDays


                val shouldUpdateLocal =
                    computedDay > crop.currentDay && computedDay in 1..crop.totalDays

                if (shouldUpdateLocal) {
                    offlineRepository.insertCrop(
                        crop.copy(currentDay = computedDay),
                        currentUserId
                    )

                }

                _uiState.value = ChangeCropUiState(
                    cropType = crop.cropType,
                    currentDay = uiDay,
                    totalDays = crop.totalDays,
                    daysLeft = daysLeft,
                    isButtonEnabled = isHarvestReady,
                    isLoading = false
                )
            }
        }
    }

}