package com.example.growbox.screen.home.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.OfflineRepository
import com.example.growbox.data.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val growBoxRepository: GrowBoxRepository,
    private val offlineRepository: OfflineRepository
) : ViewModel() {


    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""


    val userState: StateFlow<User?> = offlineRepository.getUserStream(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val cropState: StateFlow<Crop?> = offlineRepository.getCropStream(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val userEmail: String = growBoxRepository.getCurrentUserEmail() ?: "user@mail.com"

    init {
        refreshProfile()
    }


    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                growBoxRepository.signOut()
                onLogoutSuccess()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Logout failed", e)
            }
        }
    }


    fun refreshProfile() {
        viewModelScope.launch {
            try {
                if (currentUserId.isNotEmpty()) {
                    growBoxRepository.fetchCurrentCrop(currentUserId)
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Sync failed", e)
            }
        }
    }
}