package com.example.growbox.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineCropRepository
import com.example.growbox.data.model.OfflineRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
) : ViewModel() {

    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""

    private val _cropState = MutableStateFlow<Crop?>(null)
    val cropState: StateFlow<Crop?> = _cropState.asStateFlow()

    init {

        if (currentUserId.isNotEmpty()) {


            viewModelScope.launch(Dispatchers.IO) {

                growBoxRepository.syncAllCrops(currentUserId,)

                val local = growBoxRepository.getLocalCropOnce(currentUserId)
                if (local != null && local.cropId.isNotBlank()) {
                    try {
                        growBoxRepository.syncCropDay(currentUserId, local.cropId)
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "syncCropDay failed", e)
                    }
                }
            }


            viewModelScope.launch(Dispatchers.IO) {
                _cropState.value = growBoxRepository.getLocalCropOnce(currentUserId)
            }


            viewModelScope.launch {
                offlineRepository.getCropStream(currentUserId).collect { crop ->
                    if (crop != null && crop != _cropState.value) {
                        _cropState.value = crop
                    }
                }
            }


            growBoxRepository.observeCropRealtime(currentUserId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        growBoxRepository.stopObserveCropRealtime()
    }

    fun toggleVent(cropId: String, isActive: Boolean) {
        viewModelScope.launch {
            try {
                growBoxRepository.updateVentStatus(currentUserId, cropId, isActive)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to update vent", e)
            }
        }
    }

    fun toggleWatering(cropId: String, isActive: Boolean) {
        viewModelScope.launch {
            try {
                growBoxRepository.updateWateringStatus(currentUserId, cropId, isActive)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to update watering", e)
            }
        }
    }
}