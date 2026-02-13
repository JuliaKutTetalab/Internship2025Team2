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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.growbox.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.home.light_chart.LightViewModel
import com.example.growbox.screen.profile.historic_data.components.HistoricDataCard


object HistoricDataDestination : NavigationDestination {
    override val route = "historic_data"
    override val titleRes = null
    override val showBottomBar: Boolean = false

}
@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun HistoricDataScreen (
    onNavigateBack: () -> Unit
){
    //Поки нема View Model
//    val viewModel: ChartViewModel = viewModel()
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.loadData()
//    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.historic_title),
                        fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_data_screen),
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
    ) { padding ->
        Column(
            modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding)
        ){
            //Щоб не вибивало помилок поки закоментила
//                HistoricDataCard(
//                    iconRes = R.drawable.ic_light_icon,
//                    title = stringResource(R.string.light_title),
//                    onClick = onNavigateToLight
//                )
//                HistoricDataCard(
//                    iconRes = R.drawable.ic_temperature_icon,
//                    title = stringResource(R.string.temperature_title),
//                    onClick = onNavigateToTemperature
//                )
//                HistoricDataCard(
//                    iconRes = R.drawable.ic_humidity_icon,
//                    title = stringResource(R.string.humidity_title),
//                    onClick = onNavigateToHumidity
//                )
//                HistoricDataCard(
//                    iconRes = R.drawable.ic_nutrition_icon,
//                    title = stringResource(R.string.nutrition_title),
//                    onClick = onNavigateToBack
//                )
            }
    }
}