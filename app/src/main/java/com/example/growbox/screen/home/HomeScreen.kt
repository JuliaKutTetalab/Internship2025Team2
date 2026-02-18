package com.example.growbox.screen.home

import android.util.Log
import com.example.growbox.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.data.model.Crop
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.home.components.PlantHeader
import com.example.growbox.screen.home.components.SensorCard
import com.example.growbox.screen.home.components.ToggleCard
import com.example.growbox.ui.theme.White

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_title
    override val showBottomBar: Boolean = true
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToLight: (String) -> Unit,
    onNavigateToTemperature: (String) -> Unit,
    onNavigateToHumidity: (String) -> Unit,
    onNavigateToNutrition: (String) -> Unit,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cropState by viewModel.cropState.collectAsState()

    if (cropState == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    SideEffect {
        Log.d("UI_DEBUG", "HOME cropId=${cropState?.cropId} vent=${cropState?.isVentOn} water=${cropState?.isWateringOn}")
    }

    HomeScreenContent(
        modifier = modifier,
        cropState = cropState,
        onNavigateToLight = {
            val currentId = cropState?.cropId
            if (!currentId.isNullOrEmpty()) {
                onNavigateToLight(currentId)
            }
        },
        onNavigateToTemperature = {
            cropState?.cropId?.let { id -> onNavigateToTemperature(id) }
        },
        onNavigateToHumidity = {
            cropState?.cropId?.let { id -> onNavigateToHumidity(id) }
        },
        onNavigateToNutrition = {
            cropState?.cropId?.let { id -> onNavigateToNutrition(id) }
        },
        onToggleVent = { newStatus ->
            cropState?.cropId?.let { id ->
                viewModel.toggleVent(id, newStatus)
            }
        },
        onToggleWatering = { newStatus ->
            cropState?.cropId?.let { id ->
                viewModel.toggleWatering(id, newStatus)
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    cropState: Crop?,
    onNavigateToLight: () -> Unit,
    onNavigateToTemperature: () -> Unit,
    onNavigateToHumidity: () -> Unit,
    onNavigateToNutrition: () -> Unit,
    onToggleVent: (Boolean) -> Unit,
    onToggleWatering: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_24)))
        PlantHeader(cropState)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_extra_large)))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                iconRes = R.drawable.ic_light_icon,
                value = "${cropState?.light ?: 0}%",
                label = "Light",
                onClick = onNavigateToLight,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
            SensorCard(
                iconRes = R.drawable.ic_temperature_icon,
                value = "${cropState?.temperature ?: 0} °C",
                label = "Temperature",
                onClick = onNavigateToTemperature,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                iconRes = R.drawable.ic_humidity_icon,
                value = "${cropState?.humidity ?: 0}%",
                label = "Humidity",
                onClick = onNavigateToHumidity,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
            SensorCard(
                iconRes = R.drawable.ic_nutrition_icon,
                value = "${cropState?.nutrition ?: 0}%",
                label = "Nutrition",
                onClick = onNavigateToNutrition,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        Row(modifier = Modifier.fillMaxWidth()) {
            ToggleCard(
                iconRes = R.drawable.ic_vent,
                label = "Vent",
                isActive = cropState?.isVentOn ?: false,
                onToggle = onToggleVent,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
            ToggleCard(
                iconRes = R.drawable.ic_watering,
                label = "Watering",
                isActive = cropState?.isWateringOn ?: false,
                onToggle = onToggleWatering,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_extra_large)))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val mockCrop = Crop(
        cropId = "test_id",
        cropType = "Microgreens",
        currentDay = 12,
        totalDays = 21,
        light = 60,
        temperature = 24,
        humidity = 58,
        nutrition = 78,
        isVentOn = true,
        isWateringOn = false
    )

    HomeScreenContent(
        modifier = Modifier,
        cropState = mockCrop,
        onNavigateToLight = {},
        onNavigateToTemperature = {},
        onNavigateToHumidity = {},
        onNavigateToNutrition = {},
        onToggleVent = {},
        onToggleWatering = {}
    )
}
