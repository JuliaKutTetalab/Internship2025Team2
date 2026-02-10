package com.example.growbox.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineCropRepository
import com.example.growbox.data.model.OfflineRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
) : ViewModel() {

    //  UID один раз
    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""

    val cropState: StateFlow<Crop?> = offlineRepository.getCropStream(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    init {
        syncData()
    }

    fun syncData() {
        viewModelScope.launch {
            try {
                if (currentUserId.isNotEmpty()) {

                    growBoxRepository.fetchCurrentCrop(currentUserId)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Sync failed", e)
            }
        }
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
