package com.example.growbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.LogInScreen
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SignUpScreen
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.auth.auth.SplashScreen
import com.example.growbox.screen.home.humidity_chart.HumidityChartDestination
import com.example.growbox.screen.home.humidity_chart.HumidityChartScreen
import com.example.growbox.screen.home.light_chart.LightChartDestination
import com.example.growbox.screen.home.light_chart.LightChartScreen
import com.example.growbox.screen.home.nutrition_chart.NutritionChartDestination
import com.example.growbox.screen.home.nutrition_chart.NutritionChartScreen
import com.example.growbox.screen.home.temperature_chart.TemperatureChartDestination
import com.example.growbox.screen.home.temperature_chart.TemperatureChartScreen
import com.example.growbox.screen.settings.SettingsDestination
import com.example.growbox.screen.settings.SettingsScreen


@Composable
fun GrowBoxNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route ,
        modifier = modifier
    ) {

        // 1. SPLASH SCREEN
        composable(route = SplashDestination.route) {
            SplashScreen(
                onAuthSuccess = {
                    //  НІЧОГО НЕ РОБИМО.
                },
                onAuthFailure = {

                    navController.navigate(SignUpDestination.route) {
                        popUpTo(SplashDestination.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. LOGIN SCREEN
        composable(route = LogInDestination.route) {
            LogInScreen(
                // НІЧОГО ПОКИ НЕ РОБИМО

                onLoginSuccess = {

                    navController.popBackStack()
                },
                onNavigateToSingUp = {
                    navController.navigate(SignUpDestination.route)
                }
            )
        }


        composable(route = SignUpDestination.route) {
            SignUpScreen(

                onRegistrationSuccess = {

                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(LogInDestination.route) {
                        popUpTo(LogInDestination.route) { inclusive = true }
                    }
                }
            )
        }

        //LIGHT CHART SCREEN
        composable(route = LightChartDestination.route){
            LightChartScreen (
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        //Humidity CHART SCREEN
        composable(route = HumidityChartDestination.route){
            HumidityChartScreen (
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        //Temperature CHART SCREEN
        composable(route = TemperatureChartDestination.route){
            TemperatureChartScreen (
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
}
        
        //Nutrition CHART SCREEN
        composable(route = NutritionChartDestination.route){
            NutritionChartScreen (
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

            //SETTING SCREEN
            composable(route = SettingsDestination.route){
                SettingsScreen ()
            }

        }
    }
}

