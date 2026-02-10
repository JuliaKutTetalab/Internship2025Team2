package com.example.growbox.screen.home



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
    onNavigateToLight: () -> Unit,
    onNavigateToTemperature: () -> Unit,
    onNavigateToHumidity: () -> Unit,
    onNavigateToNutrition: () -> Unit,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cropState by viewModel.cropState.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        cropState = cropState,
        onNavigateToLight = onNavigateToLight,
        onNavigateToTemperature = onNavigateToTemperature,
        onNavigateToHumidity = onNavigateToHumidity,
        onNavigateToNutrition = onNavigateToNutrition,
        onToggleVent = { isActive ->
            cropState?.let { viewModel.toggleVent(it.cropId, isActive) }
        },
        onToggleWatering = { isActive ->
            cropState?.let { viewModel.toggleWatering(it.cropId, isActive) }
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier : Modifier,
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
            .background(Color(0xFFF8F8F8))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        PlantHeader(cropState) //
        Spacer(modifier = Modifier.height(32.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                iconRes = R.drawable.ic_light_icon,
                value = "${cropState?.light ?: 0}%",
                label = "Light",
                onClick = onNavigateToLight,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SensorCard(
                iconRes = R.drawable.ic_temperature_icon,
                value = "${cropState?.temperature ?: 0} °C",
                label = "Temperature",
                onClick = onNavigateToTemperature,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                iconRes = R.drawable.ic_humidity_icon,
                value = "${cropState?.humidity ?: 0}%",
                label = "Humidity",
                onClick = onNavigateToHumidity,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SensorCard(
                iconRes = R.drawable.ic_nutrion_icon,
                value = "${cropState?.nutrition ?: 0}%",
                label = "Nutrition",
                onClick = onNavigateToNutrition,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Картки перемикачів
        Row(modifier = Modifier.fillMaxWidth()) {
            ToggleCard(
                iconRes = R.drawable.ic_vent,
                label = "Vent",
                isActive = cropState?.isVentOn ?: false,
                onToggle = onToggleVent,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            ToggleCard(
                iconRes = R.drawable.ic_watering,
                label = "Watering",
                isActive = cropState?.isWateringOn ?: false,
                onToggle = onToggleWatering,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
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
