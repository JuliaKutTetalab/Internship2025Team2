package com.example.growbox.screen.settings

import androidx.lifecycle.ViewModel
import com.example.growbox.screen.settings.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class SettingsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleVenting(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            venting = _uiState.value.venting.copy(isEnabled = enabled)
        )
    }
    fun updateVentingHours(hours: Float) {
        _uiState.value = _uiState.value.copy(
            venting = _uiState.value.venting.copy(hours = hours)
        )
    }

    fun toggleLightening(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            lightening = _uiState.value.lightening.copy(isEnabled = enabled)
        )
    }
    fun updateLighteningHours(hours: Float) {
        _uiState.value = _uiState.value.copy(
            lightening = _uiState.value.lightening.copy(hours = hours)
        )
    }

    fun toggleTemperature(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            temperature = _uiState.value.temperature.copy(isEnabled = enabled)
        )
    }
    fun updateTemperatureDegrees(degrees: Float) {
        _uiState.value = _uiState.value.copy(
            temperature = _uiState.value.temperature.copy(degrees = degrees)
        )
    }

    fun toggleHumidity(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            humidity = _uiState.value.humidity.copy(isEnabled = enabled)
        )
    }
    fun updateHumidityPercentage(percentage: Float) {
        _uiState.value = _uiState.value.copy(
            humidity = _uiState.value.humidity.copy(percentage = percentage)
        )
    }

    fun toggleNutrition(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            nutrition = _uiState.value.nutrition.copy(isEnabled = enabled)
        )
    }
    fun updateNutritionMilligrams(milligrams: Float) {
        _uiState.value = _uiState.value.copy(
            nutrition = _uiState.value.nutrition.copy(milligrams = milligrams)
        )
    }
    fun updateNutritionFrequency(frequency: String) {
        _uiState.value = _uiState.value.copy(
            nutrition = _uiState.value.nutrition.copy(frequency = frequency)
        )
    }

    fun toggleWatering(enabled: Boolean){
        _uiState.value = _uiState.value.copy(
            watering = _uiState.value.watering.copy(isEnabled = enabled)
        )
    }
    fun updateWateringMilligrams(milligrams: Float) {
        _uiState.value = _uiState.value.copy(
            watering = _uiState.value.watering.copy(milligrams = milligrams)
        )
    }
    fun updateWateringFrequency(frequency: String) {
        _uiState.value = _uiState.value.copy(
            watering = _uiState.value.watering.copy(frequency = frequency)
        )
    }

}