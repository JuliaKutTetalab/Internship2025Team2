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

class ChangeCropViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
): ViewModel() {
    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""

    private val _uiState = MutableStateFlow(ChangeCropUiState())
    val uiState: StateFlow<ChangeCropUiState> = _uiState.asStateFlow()

    fun loadData(){
        viewModelScope.launch {
            offlineRepository.getCropStream(currentUserId).collect {crop ->
                if (crop != null) {
                    val daysLeft = crop.totalDays - crop.currentDay
                    val isHarvestReady = crop.currentDay >= crop.totalDays

                    _uiState.value = ChangeCropUiState(
                        cropType = crop.cropType,
                        currentDay = crop.currentDay,
                        totalDays = crop.totalDays,
                        daysLeft = daysLeft,
                        isButtonEnabled = isHarvestReady,
                        isLoading = false
                    )
                }
            }
        }
    }
}