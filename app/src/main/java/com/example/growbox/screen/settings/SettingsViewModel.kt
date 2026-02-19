package com.example.growbox.screen.settings

import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.screen.settings.model.SettingsUiState




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.screen.settings.model.HumiditySettings
import com.example.growbox.screen.settings.model.LighteningSettings
import com.example.growbox.screen.settings.model.NutritionSettings
import com.example.growbox.screen.settings.model.TemperatureSettings
import com.example.growbox.screen.settings.model.VentingSettings
import com.example.growbox.screen.settings.model.WateringSettings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val growBoxRepository: GrowBoxRepository
) : ViewModel() {

    private val currentUserId: String = growBoxRepository.getCurrentUserId() ?: ""


    private val _uiState = MutableStateFlow<SettingsUiState?>(null)
    val uiState: StateFlow<SettingsUiState?> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            growBoxRepository.getLocalCropStream(currentUserId)
                .filterNotNull()
                .distinctUntilChanged()
                .collect { crop ->
                    val newState = SettingsUiState(
                        venting = VentingSettings(
                            isEnabled = crop.isVentOn,
                            hours = crop.ventHours.toFloat()
                        ),
                        lightening = LighteningSettings(
                            isEnabled = crop.isLightOn,
                            hours = crop.light.toFloat()
                        ),
                        temperature = TemperatureSettings(degrees = crop.temperature.toFloat()),
                        humidity = HumiditySettings(percentage = crop.humidity.toFloat()),
                        nutrition = NutritionSettings(milligrams = crop.nutrition.toFloat()),
                        watering = WateringSettings(milligrams = crop.watering.toFloat())
                    )

                    if (_uiState.value != newState) {
                        _uiState.value = newState
                    }
                }
        }
    }

    fun toggleVenting(enabled: Boolean) {
        _uiState.value = _uiState.value?.copy(
            venting = _uiState.value!!.venting.copy(isEnabled = enabled)
        )
        launchUpdate { cropId -> growBoxRepository.updateVentStatus(currentUserId, cropId, enabled) }
    }

    fun updateVentingHours(hours: Float) {
        _uiState.value = _uiState.value?.copy(
            venting = _uiState.value!!.venting.copy(hours = hours)
        )
    }

    fun commitVentingHours() {
        val hours = _uiState.value?.venting?.hours?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateVentHours(currentUserId, cropId, hours) }
    }

    fun toggleLightening(enabled: Boolean) {
        _uiState.value = _uiState.value?.copy(
            lightening = _uiState.value!!.lightening.copy(isEnabled = enabled)
        )
        launchUpdate { cropId -> growBoxRepository.updateLightStatus(currentUserId, cropId, enabled) }
    }

    fun updateLightValue(hours: Float) {
        _uiState.value = _uiState.value?.copy(
            lightening = _uiState.value!!.lightening.copy(hours = hours)
        )
    }

    fun commitLightValue() {
        val hours = _uiState.value?.lightening?.hours?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateLightValue(currentUserId, cropId, hours) }
    }

    fun updateTemperatureDegrees(degrees: Float) {
        _uiState.value = _uiState.value?.copy(
            temperature = _uiState.value!!.temperature.copy(degrees = degrees)
        )
    }

    fun commitTemperatureDegrees() {
        val deg = _uiState.value?.temperature?.degrees?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateCurrentTemperature(currentUserId, cropId, deg) }
    }

    fun updateHumidityPercentage(percentage: Float) {
        _uiState.value = _uiState.value?.copy(
            humidity = _uiState.value!!.humidity.copy(percentage = percentage)
        )
    }

    fun commitHumidityPercentage() {
        val hum = _uiState.value?.humidity?.percentage?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateCurrentHumidity(currentUserId, cropId, hum) }
    }

    fun updateNutritionMilligrams(mg: Float) {
        _uiState.value = _uiState.value?.copy(
            nutrition = _uiState.value!!.nutrition.copy(milligrams = mg)
        )
    }

    fun commitNutritionMilligrams() {
        val mg = _uiState.value?.nutrition?.milligrams?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateNutritionValue(currentUserId, cropId, mg) }
    }

    fun updateWateringMilligrams(mg: Float) {
        _uiState.value = _uiState.value?.copy(
            watering = _uiState.value!!.watering.copy(milligrams = mg)
        )
    }

    fun commitWateringMilligrams() {
        val mg = _uiState.value?.watering?.milligrams?.toInt() ?: return
        launchUpdate { cropId -> growBoxRepository.updateWateringValue(currentUserId, cropId, mg) }
    }

    private fun launchUpdate(block: suspend (String) -> Unit) {
        viewModelScope.launch {
            val local = growBoxRepository.getLocalCropOnce(currentUserId)
            val cropId = local?.cropId
            if (!cropId.isNullOrEmpty()) block(cropId)
        }
    }
}
