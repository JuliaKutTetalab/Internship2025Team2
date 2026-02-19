package com.example.growbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.LogInScreen
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SignUpScreen
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.auth.auth.SplashScreen
import com.example.growbox.screen.bluetooth.connected.ConnectedDeviceDestination
import com.example.growbox.screen.bluetooth.connected.ConnectedDeviceScreen
import com.example.growbox.screen.bluetooth.initial.InitialDeviceDestination
import com.example.growbox.screen.bluetooth.initial.InitialDeviceScreen
import com.example.growbox.screen.bluetooth.select_device.ConnectDeviceDestination
import com.example.growbox.screen.bluetooth.select_device.ConnectDeviceScreen
import com.example.growbox.screen.home.chart.ChartDestination
import com.example.growbox.screen.home.chart.ChartScreen
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.HomeScreen
import com.example.growbox.screen.home.chart.model.ChartType

import com.example.growbox.screen.onboarding.components.OnBoardingScreen
import com.example.growbox.screen.onboarding.components.OnBoardingScreenDestination

import com.example.growbox.screen.profile.ProfileDestination
import com.example.growbox.screen.profile.ProfileScreen

import com.example.growbox.screen.profile.change_crop_type.ChangeCropTypeDestination
import com.example.growbox.screen.profile.change_crop_type.ChangeCropTypeScreen
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.SelectCropTypeDestination
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.SelectCropTypeScreen
import com.example.growbox.screen.profile.historic_data.HistoricDataDestination
import com.example.growbox.screen.profile.historic_data.HistoricDataScreen
import com.example.growbox.screen.profile.historic_data.chart_screen.HistoricChartDestination
import com.example.growbox.screen.profile.historic_data.chart_screen.HistoricChartScreen
import com.example.growbox.screen.profile.my_harvest.MyHarvestDestination
import com.example.growbox.screen.profile.my_harvest.MyHarvestScreen
import com.example.growbox.screen.settings.SettingsDestination
import com.example.growbox.screen.settings.SettingsScreen

import com.example.growbox.screen.bluetooth.initial.InitialDeviceDestination
import com.example.growbox.screen.bluetooth.initial.InitialDeviceScreen
import com.example.growbox.screen.bluetooth.searching.SearchingDeviceDestination
import com.example.growbox.screen.bluetooth.searching.SearchingDeviceScreen
import com.example.growbox.screen.bluetooth.select_device.ConnectDeviceDestination
import com.example.growbox.screen.bluetooth.select_device.ConnectDeviceScreen
import com.example.growbox.screen.bluetooth.connecting.ConnectingDestination
import com.example.growbox.screen.bluetooth.connecting.ConnectingScreen
import com.example.growbox.screen.bluetooth.conected.ConnectedDeviceDestination
import com.example.growbox.screen.bluetooth.conected.ConnectedDeviceScreen

private const val INITIAL_SELECT_CROP_ROUTE = "initial_select_crop"
private const val INITIAL_CHANGE_CROP_ROUTE = "initial_change_crop"

@Composable
fun GrowBoxNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {

        composable(route = SplashDestination.route) {
            SplashScreen(
                onAuthSuccess = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(SplashDestination.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onAuthFailure = {
                    navController.navigate(SignUpDestination.route) {
                        popUpTo(SplashDestination.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = MyHarvestDestination.route) {
            MyHarvestScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = LogInDestination.route) {
            LogInScreen(
                onLoginSuccess = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(OnBoardingScreenDestination.route) { inclusive = true }
                    }
                },
                onNavigateToSingUp = {
                    navController.navigate(SignUpDestination.route)
                }
            )
        }

        composable(route = SignUpDestination.route) {
            SignUpScreen(
                onRegistrationSuccess = {
                    navController.navigate(OnBoardingScreenDestination.route) {
                        popUpTo(SignUpDestination.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(LogInDestination.route) {
                        popUpTo(LogInDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = OnBoardingScreenDestination.route) {
            OnBoardingScreen(
                onNavigateToHome = {
                    navController.navigate(InitialDeviceDestination.route) {
                        popUpTo(OnBoardingScreenDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = modifier,
                onNavigateToLight = { cropId ->
                    if (cropId.isNotEmpty()) {
                        navController.navigate("${ChartDestination.route}/$cropId/LIGHT")
                    }
                },
                onNavigateToTemperature = { cropId ->
                    if (cropId.isNotEmpty()) {
                        navController.navigate("${ChartDestination.route}/$cropId/TEMPERATURE")
                    }
                },
                onNavigateToHumidity = { id ->
                    navController.navigate("${ChartDestination.route}/$id/HUMIDITY")
                },
                onNavigateToNutrition = { id ->
                    navController.navigate("${ChartDestination.route}/$id/NUTRITION")
                }
            )
        }

        composable(route = ProfileDestination.route) {
            ProfileScreen(
                onNavigateToLogin = {
                    navController.navigate(LogInDestination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToChangeCrop = {
                    navController.navigate(ChangeCropTypeDestination.route)
                },
                onNavigateToMyHarvest = {
                    navController.navigate(MyHarvestDestination.route)
                },
                onNavigateToHistoric = {
                    navController.navigate(HistoricDataDestination.route)
                }
            )
        }

        composable(
            route = HistoricChartDestination.routeWithArgs,
            arguments = listOf(
                navArgument(HistoricChartDestination.chartTypeArg) { type = NavType.StringType }
            )
        ) {
            HistoricChartScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = HistoricDataDestination.route) {
            HistoricDataScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLight = {
                    navController.navigate("${HistoricChartDestination.route}/${ChartType.LIGHT.name}")
                },
                onNavigateToTemperature = {
                    navController.navigate("${HistoricChartDestination.route}/${ChartType.TEMPERATURE.name}")
                },
                onNavigateToHumidity = {
                    navController.navigate("${HistoricChartDestination.route}/${ChartType.HUMIDITY.name}")
                },
                onNavigateToNutrition = {
                    navController.navigate("${HistoricChartDestination.route}/${ChartType.NUTRITION.name}")
                },
            )
        }

        composable(
            route = ChartDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ChartDestination.cropIdArg) { type = NavType.StringType },
                navArgument(ChartDestination.chartTypeArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chartType = backStackEntry.arguments?.getString(ChartDestination.chartTypeArg) ?: "LIGHT"
            ChartScreen(
                chartType = chartType,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = SettingsDestination.route) {
            SettingsScreen()
        }

        composable(route = ChangeCropTypeDestination.route) {
            ChangeCropTypeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSelectCropType = {
                    navController.navigate(SelectCropTypeDestination.route)
                }
            )
        }

        composable(route = SelectCropTypeDestination.route) {
            SelectCropTypeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = INITIAL_SELECT_CROP_ROUTE) {
            SelectCropTypeScreen(
                onNavigateBack = {
                    navController.navigate(INITIAL_CHANGE_CROP_ROUTE) {
                        popUpTo(INITIAL_SELECT_CROP_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(route = INITIAL_CHANGE_CROP_ROUTE) {
            ChangeCropTypeScreen(
                onNavigateBack = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToSelectCropType = {
                    navController.navigate(INITIAL_SELECT_CROP_ROUTE)
                }
            )
        }

        composable(route = InitialDeviceDestination.route) {
            InitialDeviceScreen(
                onConnectClick = {
                    navController.navigate(SearchingDeviceDestination.route)
                }
            )
        }

        composable(route = SearchingDeviceDestination.route) {
            SearchingDeviceScreen(
                onCancel = { navController.popBackStack() },
                onDevicesFound = {
                    navController.navigate(ConnectDeviceDestination.route) {
                        popUpTo(SearchingDeviceDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = ConnectDeviceDestination.route) {
            ConnectDeviceScreen(
                onNavigateBack = { navController.popBackStack() },
                onDeviceSelected = {
                    navController.navigate(ConnectingDestination.route)
                }
            )
        }

        composable(route = ConnectingDestination.route) {
            ConnectingScreen(
                onCancel = { navController.popBackStack() },
                onConnected = {
                    navController.navigate(ConnectedDeviceDestination.route) {
                        popUpTo(InitialDeviceDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = ConnectedDeviceDestination.route) {
            ConnectedDeviceScreen(
                onNavigateBack = { navController.popBackStack() },
                onNextClick = {
                    navController.navigate(INITIAL_SELECT_CROP_ROUTE) {
                        popUpTo(ConnectedDeviceDestination.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
