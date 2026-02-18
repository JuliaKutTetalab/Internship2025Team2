package com.example.growbox.screen.home.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.screen.home.chart.components.ChartGraph
import com.example.growbox.screen.home.chart.components.ChartHeader
import com.example.growbox.screen.home.chart.components.ChartStatCards
import com.example.growbox.screen.home.chart.components.ChartTabRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(
    chartType: String,
    onNavigateBack: () -> Unit,
    viewModel: ChartViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(uiState.titleRes),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.font_size_huge).value.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_home_screen),
                            tint = Color.Black
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
                .background(Color.White)
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
                period = uiState.selectedPeriod,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.spacing_medium)))
            ChartStatCards(
                currentValue = "${uiState.currentValue} ${uiState.unit}",
                recommendedValue = "${uiState.recommendedValue} ${uiState.unit}",
                weekConsumption = uiState.weekConsumption,
                totalConsumption = uiState.totalConsumption
            )
        }
    }
}
