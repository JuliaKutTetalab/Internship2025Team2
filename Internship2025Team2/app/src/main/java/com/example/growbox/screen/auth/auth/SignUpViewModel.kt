package com.example.growbox.screen.auth.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}

class SignUpViewModel(
    private val repository: GrowBoxRepository
) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun register(email: String, password: String) {
        _signUpState.value = SignUpState.Loading
        viewModelScope.launch {
            try {
                repository.registerAndInitialize(email, password)
                _signUpState.value = SignUpState.Success
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "Registration failed.")
            }
        }
    }

    fun resetState() {
        _signUpState.value = SignUpState.Idle
    }
}