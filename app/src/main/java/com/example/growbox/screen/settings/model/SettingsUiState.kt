package com.example.growbox.screen.settings.model

import androidx.compose.ui.res.stringResource
import com.example.growbox.R


data class SettingsUiState(
    val venting: VentingSettings = VentingSettings(),
    val lightening: LighteningSettings = LighteningSettings(),
    val temperature: TemperatureSettings = TemperatureSettings(),
    val humidity: HumiditySettings = HumiditySettings(),
    val nutrition: NutritionSettings = NutritionSettings(),
    val watering: WateringSettings = WateringSettings(),

)

data class VentingSettings(
    val isEnabled: Boolean = true,
    val hours: Float = 12f //1-24h
)
data class LighteningSettings(
    val isEnabled: Boolean = true,
    val hours: Float = 8f //1-16h
)
data class TemperatureSettings(
    val isEnabled: Boolean = true,
    val degrees: Float = 24f //10-36`C
)
data class HumiditySettings(
    val isEnabled: Boolean = true,
    val percentage: Float = 50f //off-100%
)
data class NutritionSettings(
    val isEnabled: Boolean = true,
    val milligrams: Float = 250f, //off-500mg
    val frequency: Int = 0
)
data class WateringSettings(
    val isEnabled: Boolean = true,
    val milligrams: Float = 250f, //off-500mg
    val frequency: Int = 0
)

