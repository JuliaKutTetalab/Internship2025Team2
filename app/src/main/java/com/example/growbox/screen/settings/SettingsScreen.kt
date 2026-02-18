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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.settings.components.SettingsCards
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.screen.home.chart.ChartViewModel
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings_title
    override val showBottomBar = true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val uiStateNullable by viewModel.uiState.collectAsState()


    val state = uiStateNullable ?: return


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.settings_title),
                            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {

            SettingsCards(
                iconRes = R.drawable.ic_vent_icon,
                title = stringResource(R.string.vent_card_title),
                isEnabled = state.venting.isEnabled,
                onToggle = viewModel::toggleVenting,
                sliderValue = state.venting.hours,
                onSliderChange = viewModel::updateVentingHours,
                onSliderChangeFinished = viewModel::commitVentingHours,

                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.unit_power_label, 0),
                currentLabel = stringResource(R.string.unit_power_label, state.venting.hours.toInt()),
                maxLabel = stringResource(R.string.unit_power_label, 100)
            )

            SettingsCards(
                iconRes = R.drawable.ic_light_icon,
                title = stringResource(R.string.light_card_title),
                isEnabled = state.lightening.isEnabled,
                onToggle = viewModel::toggleLightening,
                sliderValue = state.lightening.hours,
                onSliderChange = viewModel::updateLightValue,
                onSliderChangeFinished = viewModel::commitLightValue,

                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.unit_lumen_label, 0),
                currentLabel = stringResource(R.string.unit_lumen_label, state.lightening.hours.toInt()),
                maxLabel = stringResource(R.string.unit_lumen_label, 100)
            )

            SettingsCards(
                iconRes = R.drawable.ic_temperature_icon,
                title = stringResource(R.string.temperature_card_title),
                sliderValue = state.temperature.degrees,
                onSliderChange = viewModel::updateTemperatureDegrees,
                onSliderChangeFinished = viewModel::commitTemperatureDegrees,


                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.unit_celsius_label, 0),
                currentLabel = stringResource(R.string.unit_celsius_label, state.temperature.degrees.toInt()),
                maxLabel = stringResource(R.string.unit_celsius_label, 100)
            )

            val humidityValue = state.humidity.percentage.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_humidity_icon,
                title = stringResource(R.string.humidity_card_title),
                sliderValue = state.humidity.percentage,
                onSliderChange = viewModel::updateHumidityPercentage,
                onSliderChangeFinished = viewModel::commitHumidityPercentage,

                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (humidityValue == 0) {
                    stringResource(R.string.label_off)
                } else {
                    stringResource(R.string.unit_percent_label, humidityValue)
                },
                maxLabel = stringResource(R.string.unit_percent_label, 100)
            )

            val nutritionValue = state.nutrition.milligrams.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_nutrition_icon,
                title = stringResource(R.string.nutrition_card_title),
                frequency = state.nutrition.frequency,
                onFrequencyChange = null,
                sliderValue = state.nutrition.milligrams,
                onSliderChange = viewModel::updateNutritionMilligrams,
                onSliderChangeFinished = viewModel::commitNutritionMilligrams,

                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (nutritionValue == 0) {
                    stringResource(R.string.label_off)
                } else {
                    stringResource(R.string.unit_milligrams_label, nutritionValue)
                },
                maxLabel = stringResource(R.string.unit_milligrams_label, 100)
            )

            val wateringValue = state.watering.milligrams.toInt()
            SettingsCards(
                iconRes = R.drawable.ic_watering_icon,
                title = stringResource(R.string.water_card_title),
                frequency = state.watering.frequency,
                onFrequencyChange = null,
                sliderValue = state.watering.milligrams,
                onSliderChange = viewModel::updateWateringMilligrams,
                onSliderChangeFinished = viewModel::commitWateringMilligrams,

                sliderRange = 0f..100f,
                minLabel = stringResource(R.string.label_off),
                currentLabel = if (wateringValue == 0) {
                    stringResource(R.string.label_off)
                } else {
                    stringResource(R.string.unit_milligrams_label, wateringValue)
                },
                maxLabel = stringResource(R.string.unit_milligrams_label, 100)
            )
        }
    }
}

