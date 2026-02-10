package com.example.growbox.screen.auth.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.data.GrowBoxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val repository: GrowBoxRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {

                repository.signIn(email, password)
                _loginState.value = LoginState.Success
            } catch (e: Exception) {

                _loginState.value = LoginState.Error(e.message ?: "Login failed. Check credentials.")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}