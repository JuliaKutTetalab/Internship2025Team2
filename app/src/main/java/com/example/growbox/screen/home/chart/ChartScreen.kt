package com.example.growbox.screen.home.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.screen.home.chart.components.ChartGraph
import com.example.growbox.screen.home.chart.components.ChartHeader
import com.example.growbox.screen.home.chart.components.ChartStatCards
import com.example.growbox.screen.home.chart.components.ChartTabRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(
    chartType: String,
    onNavigateBack: () -> Unit,
    viewModel: ChartViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(uiState.title)},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.content_description_home_screen))
                    }
                }
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ChartHeader(
                iconRes = uiState.iconRes,
                description = stringResource(R.string.light_description),
                currentValue = uiState.currentValue,
                unit = uiState.unit
            )
            ChartTabRow(
                selectedPeriod = uiState.selectedPeriod,
                onPeriodSelected = viewModel::onPeriodSelected
            )

            ChartGraph(
                data = uiState.chartData,
                unit = uiState.unit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            ChartStatCards(
                currentValue = "${uiState.currentValue}${uiState.unit}",
                recommendedValue = "${uiState.recommendedValue}${uiState.unit}",
                weekConsumption = "${uiState.weekConsumption}${uiState.unit}",
                totalConsumption = "${uiState.totalConsumption}${uiState.unit}"
            )
        }
    }
}