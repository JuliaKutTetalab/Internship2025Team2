package com.example.growbox.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.growbox.R
import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.LogInScreen
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SignUpScreen
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.auth.auth.SplashScreen
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.HomeScreen
import com.example.growbox.screen.home.humidity_chart.HumidityChartDestination
import com.example.growbox.screen.home.humidity_chart.HumidityChartScreen
import com.example.growbox.screen.home.light_chart.LightChartDestination
import com.example.growbox.screen.home.light_chart.LightChartScreen
import com.example.growbox.screen.onboarding.components.OnBoardingScreen
import com.example.growbox.screen.onboarding.components.OnBoardingScreenDestination
import com.example.growbox.screen.home.nutrition_chart.NutritionChartDestination
import com.example.growbox.screen.home.nutrition_chart.NutritionChartScreen
import com.example.growbox.screen.home.profile.ProfileDestination
import com.example.growbox.screen.home.profile.ProfileScreen
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
                    navController.navigate(HomeDestination.route) {
                        popUpTo(OnBoardingScreenDestination.route) { inclusive = true }
                    }
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
        //3 SIGN UP SCREEN
        composable(route = SignUpDestination.route) {
            SignUpScreen(
                onRegistrationSuccess = {
                    navController.navigate(OnBoardingScreenDestination.route){
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

        //4 ONBOARDING SCREEN
        composable(route = OnBoardingScreenDestination.route) {
            OnBoardingScreen(
                onNavigateToHome = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(OnBoardingScreenDestination.route) { inclusive = true }
                    }
                }
            )
        }
        //HOME SCREEN
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = modifier,
                onNavigateToLight = { navController.navigate(LightChartDestination.route) },
                onNavigateToTemperature = { navController.navigate(TemperatureChartDestination.route) },
                onNavigateToHumidity = { navController.navigate(HumidityChartDestination.route) },
                onNavigateToNutrition = { navController.navigate(NutritionChartDestination.route) }
            )
        }

        composable(route = ProfileDestination.route) {
            ProfileScreen (
                onNavigateToLogin = {
                    navController.navigate(LogInDestination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }


//        composable(route = ProfileDestination.route) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text("Екран Profrile (в розробці)")
//            }
//        }








        //LIGHT CHART SCREEN
        composable(route = LightChartDestination.route) {
            LightChartScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        //Humidity CHART SCREEN
        composable(route = HumidityChartDestination.route) {
            HumidityChartScreen(
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
                } ,
            )
        }

            //SETTING SCREEN
            composable(route = SettingsDestination.route){
                SettingsScreen ()
            }

        }
    }

