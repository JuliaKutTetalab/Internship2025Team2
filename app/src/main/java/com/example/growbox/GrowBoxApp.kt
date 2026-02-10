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



import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.components.GrowBoxBottomBar
import com.example.growbox.screen.home.humidity_chart.HumidityChartDestination
import com.example.growbox.screen.home.light_chart.LightChartDestination
import com.example.growbox.screen.home.nutrition_chart.NutritionChartDestination
import com.example.growbox.screen.profile.ProfileDestination
import com.example.growbox.screen.home.temperature_chart.TemperatureChartDestination
import com.example.growbox.screen.onboarding.components.OnBoardingScreenDestination
import com.example.growbox.screen.settings.SettingsDestination
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
    SettingsDestination,
    ProfileDestination ,
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
                            // Знаходимо початковий екран графа (зазвичай це Home після Splash)
                         //   val startDest = navController.graph.findStartDestination().id

//                            popUpTo(startDest) {
//                                saveState = true
//                                // Якщо ми хочемо, щоб Home завжди скидав стек, додаємо:
//                                // inclusive = (route == HomeDestination.route)
//                            }

                        //    Log.d("GrowBoxBottomBar", route.toString()+" bottom")
                       //     popUpTo(navController.graph.startDestinationId)
//                            popUpTo(HomeDestination.route)    {
//
//                                Log.d("GrowBoxBottomBar", " popUpTo")
//                                saveState = true
//                            }

                            launchSingleTop = true
                            // Відновлення стан екрана при повторному натисканні
                            restoreState = (route != HomeDestination.route)
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

