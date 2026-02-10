package com.example.growbox.screen.home.temperature_chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.growbox.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.home.temperature_chart.TemperatureViewModel
import com.example.growbox.screen.home.temperature_chart.components.TemperatureChart
import com.example.growbox.screen.home.temperature_chart.components.TemperatureHeader
import com.example.growbox.screen.home.temperature_chart.components.TemperatureStatCards
import com.example.growbox.screen.home.temperature_chart.components.TemperatureTabRow
import com.example.growbox.ui.theme.GrowBoxTheme


object TemperatureChartDestination : NavigationDestination {
    override val route = "temperature_chart"
    override val titleRes = R.string.temperature_title
    override val showBottomBar= true
}
@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun TemperatureChartScreen (
    onNavigateBack: () -> Unit
){
    val viewModel: TemperatureViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.temperature_title),
                        fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_home_screen),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) {
            padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ){

            TemperatureHeader(
                iconRes = R.drawable.ic_temperature_icon,
                description = stringResource(R.string.temperature_description),
                currentValue = uiState.currentValue
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
            TemperatureTabRow(
                selectedPeriod = uiState.selectedPeriod,
                onPeriodSelected = { period ->
                    viewModel.onPeriodSelected(period)
                }
            )

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            TemperatureChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(dimensionResource(R.dimen.content_max_width_small))
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                data = uiState.chartData
            )

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

            TemperatureStatCards(
                currentValue = uiState.currentValue,
                recommendedValue = uiState.recommendedValue,
                weekConsumption = uiState.weekConsumption,
                totalConsumption = uiState.totalConsumption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TemperatureChartScreenPreview (){
    GrowBoxTheme {
        TemperatureChartScreen (
            onNavigateBack = {}
        )
    }
}