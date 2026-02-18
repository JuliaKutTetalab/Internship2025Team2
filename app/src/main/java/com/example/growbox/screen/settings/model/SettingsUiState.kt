package com.example.growbox.screen.settings.model

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
    val hours: Float = 50f
)

data class LighteningSettings(
    val isEnabled: Boolean = true,
    val hours: Float = 50f
)

data class TemperatureSettings(
    val isEnabled: Boolean = true,
    val degrees: Float = 25f
)

data class HumiditySettings(
    val isEnabled: Boolean = true,
    val percentage: Float = 50f
)

data class NutritionSettings(
    val isEnabled: Boolean = true,
    val milligrams: Float = 50f,
    val frequency: Int = 0
)

data class WateringSettings(
    val isEnabled: Boolean = true,
    val milligrams: Float = 50f,
    val frequency: Int = 0
)
