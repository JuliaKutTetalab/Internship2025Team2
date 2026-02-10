package com.example.growbox.screen.auth.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



sealed interface SplashState {
    data object Loading : SplashState
    data class Ready(val isLoggedIn: Boolean) : SplashState
}

class SplashScreenViewModel(
    private val repository: GrowBoxRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashState>(SplashState.Loading)
    val uiState: StateFlow<SplashState> = _uiState.asStateFlow()
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            try {
                val status = repository.isLoggedIn()
                _isLoggedIn.value = status
                _uiState.value = SplashState.Ready(status)
            } catch (e: Exception) {

                Log.e("Splash", "Authentication check failed", e)
                _isLoggedIn.value = false
            }
        }
    }
}