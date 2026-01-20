package com.example.growbox.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.settings.components.SettingsCards

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(){
    val viewModel: SettingsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                            text = stringResource(R.string.settings_title),
                            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                            fontWeight = FontWeight.Bold,
                            )
                        }
                },
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ){
            //Vent
            SettingsCards(
                iconRes = R.drawable.ic_vent_icon,
                title = stringResource(R.string.vent_card_title),
                isEnabled = uiState.venting.isEnabled,
                onToggle = viewModel::toggleVenting,
                sliderValue = uiState.venting.hours,
                onSliderChange = viewModel::updateVentingHours,
                sliderRange = 1f..24f,
                minLabel = stringResource(R.string.unit_hours_label, 1),
                currentLabel = stringResource(R.string.unit_hours_label, uiState.venting.hours.toInt()),
                maxLabel = stringResource(R.string.unit_hours_label, 24)
            )
            SettingsCards(
                iconRes = R.drawable.ic_light_icon,
                title = stringResource(R.string.light_card_title),
                isEnabled = uiState.lightening.isEnabled,
                onToggle = viewModel::toggleLightening,
                sliderValue = uiState.lightening.hours,
                onSliderChange = viewModel::updateLighteningHours,
                sliderRange = 1f..16f,
                minLabel = stringResource(R.string.unit_hours_label, 1),
                currentLabel = stringResource(R.string.unit_hours_label, uiState.lightening.hours.toInt()),
                maxLabel = stringResource(R.string.unit_hours_label, 16)
            )
            SettingsCards(
                iconRes = R.drawable.ic_temperature_icon,
                title = stringResource(R.string.temperature_card_title),
                isEnabled = uiState.temperature.isEnabled,
                onToggle = viewModel::toggleTemperature,
                sliderValue = uiState.temperature.degrees,
                onSliderChange = viewModel::updateTemperatureDegrees,
                sliderRange = 10f..36f,
                minLabel = stringResource(R.string.unit_celsius_label, 10),
                currentLabel = stringResource(R.string.unit_celsius_label, uiState.temperature.degrees.toInt()),
                maxLabel = stringResource(R.string.unit_celsius_label, 36)
            )

            val humidityValue = uiState.humidity.percentage.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_humidity_icon,
                title = stringResource(R.string.humidity_card_title),
                isEnabled = uiState.humidity.isEnabled,
                onToggle = viewModel::toggleHumidity,
                sliderValue = uiState.humidity.percentage,
                onSliderChange = viewModel::updateHumidityPercentage,
                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (humidityValue == 0){
                    stringResource(R.string.label_off)
                }else{
                    stringResource(R.string.unit_percent_label, humidityValue)
                },
                maxLabel = stringResource(R.string.unit_percent_label, 100)
            )

            val nutritionValue = uiState.nutrition.milligrams.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_nutrition_icon,
                title = stringResource(R.string.nutrition_card_title),
                isEnabled = uiState.nutrition.isEnabled,
                onToggle = viewModel::toggleNutrition,
                sliderValue = uiState.nutrition.milligrams,
                onSliderChange = viewModel::updateNutritionMilligrams,
                sliderRange = 0f..500f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (nutritionValue == 0){
                    stringResource(R.string.label_off)
                }else{
                    stringResource(R.string.unit_milligrams_label, nutritionValue)
                },
                maxLabel = stringResource(R.string.unit_milligrams_label, 500),
                frequency = uiState.watering.frequency,
                onFrequencyChange = viewModel::updateWateringFrequency
            )

            val wateringValue = uiState.watering.milligrams.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_watering_icon,
                title = stringResource(R.string.water_card_title),
                isEnabled = uiState.watering.isEnabled,
                onToggle = viewModel::toggleNutrition,
                sliderValue = uiState.watering.milligrams,
                onSliderChange = viewModel::updateWateringMilligrams,
                sliderRange = 0f..500f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (wateringValue == 0){
                    stringResource(R.string.label_off)
                }else{
                    stringResource(R.string.unit_milligrams_label, wateringValue)
                },
                maxLabel = stringResource(R.string.unit_milligrams_label, 500),
                frequency = uiState.watering.frequency,
                onFrequencyChange = viewModel::updateWateringFrequency
            )
        }
    }
}
