package com.example.growbox


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.growbox.navigation.GrowBoxNavHost
import com.example.growbox.navigation.NavigationDestination



import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.bluetooth.connected.ConnectedDeviceDestination
import com.example.growbox.screen.bluetooth.initial.InitialDeviceDestination
import com.example.growbox.screen.bluetooth.select_device.ConnectDeviceDestination
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.chart.ChartDestination
import com.example.growbox.screen.home.components.GrowBoxBottomBar

import com.example.growbox.screen.profile.ProfileDestination

import com.example.growbox.screen.onboarding.components.OnBoardingScreenDestination
import com.example.growbox.screen.settings.SettingsDestination
import com.example.growbox.screen.profile.change_crop_type.ChangeCropTypeDestination
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.SelectCropTypeDestination
import com.example.growbox.screen.profile.historic_data.HistoricDataDestination
import com.example.growbox.screen.profile.historic_data.chart_screen.HistoricChartDestination
import com.example.growbox.screen.profile.my_harvest.MyHarvestDestination


val allDestinations: List<NavigationDestination> = listOf(
    HomeDestination,
    SettingsDestination,
    ProfileDestination,
    ChangeCropTypeDestination,
    SelectCropTypeDestination,
    ChartDestination,
    MyHarvestDestination,
    LogInDestination,
    SignUpDestination,
    OnBoardingScreenDestination,
    SplashDestination,
    HistoricChartDestination,
    HistoricDataDestination
    TemperatureChartDestination,
    NutritionChartDestination,
    SettingsDestination,
    ProfileDestination ,
    ChangeCropTypeDestination,
    InitialDeviceDestination,
    ConnectDeviceDestination,
    ConnectedDeviceDestination
)

@Composable
fun GrowBoxApp() {

    val navController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val currentDestination: NavigationDestination? = allDestinations.find { destination ->
        currentRoute == destination.route || (currentRoute?.startsWith("chart") == true && destination.route == "chart")
    }

    Scaffold(

        bottomBar = {
            if (currentDestination?.showBottomBar == true) {
                GrowBoxBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (route == HomeDestination.route) {

                            val popped = navController.popBackStack(HomeDestination.route, inclusive = false)
                            if (!popped) {
                                navController.navigate(HomeDestination.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(HomeDestination.route) { saveState = true }
                                }
                            }
                            return@GrowBoxBottomBar
                        }

                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(HomeDestination.route) {
                                saveState = true
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        GrowBoxNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

