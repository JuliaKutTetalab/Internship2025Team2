package com.example.growbox.screen.profile.historic_data.chart_screen





import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.ui.theme.GreenLight




import androidx.compose.ui.unit.sp
import com.example.growbox.screen.profile.historic_data.components.ValueCard

import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.home.chart.components.ChartGraph
import com.example.growbox.screen.home.chart.model.ChartType
import com.example.growbox.screen.profile.historic_data.model.HistoricChartViewModel

object HistoricChartDestination : NavigationDestination {
    override val route = "historic_chart"
    override val titleRes = null
    override val showBottomBar = true

    const val chartTypeArg = "chartType"
    val routeWithArgs = "$route/{$chartTypeArg}"
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricChartScreen(
    onNavigateBack: () -> Unit,
    viewModel: HistoricChartViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()


    val screenTitle = when (viewModel.chartType) {
        ChartType.LIGHT -> stringResource(R.string.light_title)
        ChartType.TEMPERATURE -> stringResource(R.string.temperature_title)
        ChartType.HUMIDITY -> stringResource(R.string.humidity_title)
        ChartType.NUTRITION -> stringResource(R.string.nutrition_title)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        fontSize = dimensionResource(R.dimen.plant_header_title_size).value.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = GreenLight) }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            HistoricChartTabRow(
                selectedPeriod = uiState.selectedPeriod,
                onPeriodSelected = viewModel::onPeriodSelected,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))


            ChartGraph(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                unit = uiState.unit,
                data = uiState.data
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                ValueCard(
                    iconRes = R.drawable.ic_lowest_icon,
                    label = stringResource(R.string.lowest_label),
                    value = "${uiState.lowestValue}${uiState.unit}",
                    date = uiState.lowestData,
                    modifier = Modifier.weight(1f)
                )
                ValueCard(
                    iconRes = R.drawable.ic_highest_icon,
                    label = stringResource(R.string.highest_label),
                    value = "${uiState.highestValue}${uiState.unit}",
                    date = uiState.highestData,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}