package com.example.growbox.screen.profile.historic_data



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.growbox.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.profile.historic_data.components.HistoricDataCard
import com.example.growbox.screen.profile.historic_data.components.ValueCard
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

object HistoricDataDestination : NavigationDestination {
    override val route = "historic_data"
    override val titleRes = null
    override val showBottomBar: Boolean = true
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoricDataScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLight: () -> Unit,
    onNavigateToTemperature: () -> Unit,
    onNavigateToHumidity: () -> Unit,
    onNavigateToNutrition: () -> Unit,
) {
    val items = listOf(
        Triple(R.drawable.ic_light_icon, R.string.light_title, onNavigateToLight),
        Triple(R.drawable.ic_temperature_icon, R.string.temperature_title, onNavigateToTemperature),
        Triple(R.drawable.ic_humidity_icon, R.string.humidity_title, onNavigateToHumidity),
        Triple(R.drawable.ic_nutrition_icon, R.string.nutrition_title, onNavigateToNutrition),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.historic_title),
                        fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            items(items) { (icon, titleRes, onClick) ->
                HistoricDataCard(
                    iconRes = icon,
                    title = stringResource(titleRes),
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.9f)
                )
            }
        }
    }
}