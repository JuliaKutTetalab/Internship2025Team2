package com.example.growbox

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.growbox.navigation.GrowBoxNavHost
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.navigation.NutritionChartDestination
import com.example.growbox.navigation.TemperatureChartDestination
import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.components.GrowBoxBottomBar
import com.example.growbox.screen.home.humidity_chart.HumidityChartDestination
import com.example.growbox.screen.home.light_chart.LightChartDestination
import com.example.growbox.screen.onboarding.components.OnBoardingScreenDestination
import com.example.growbox.screen.profile.change_crop_type.ChangeCropTypeDestination


val allDestinations: List<NavigationDestination> = listOf(
    HomeDestination,
    LightChartDestination,
    HumidityChartDestination,
    LogInDestination,
    SignUpDestination,
    OnBoardingScreenDestination,
    SplashDestination,
    TemperatureChartDestination,
    NutritionChartDestination,
    ChangeCropTypeDestination,
)

@Composable
fun GrowBoxApp() {

    val navController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val currentDestination: NavigationDestination? = allDestinations.find { it.route == currentRoute }



    Scaffold(

        bottomBar = {
            // Показуємо панель ТІЛЬКИ якщо showBottomBar == true в об'єкті Destination
            if (currentDestination?.showBottomBar == true) {
                GrowBoxBottomBar(

                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        Log.d("GrowBoxBottomBar", currentRoute.toString()+" bottom")
                        navController.navigate(route) {

                            Log.d("GrowBoxBottomBar", route.toString()+" bottom")
                            popUpTo(navController.graph.startDestinationId) {

                                Log.d("GrowBoxBottomBar", " popUpTo")
                                saveState = true
                            }

                            launchSingleTop = true
                            // Відновлення стан екрана при повторному натисканні
                            restoreState = true
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

