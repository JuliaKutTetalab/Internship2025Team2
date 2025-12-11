package com.example.growbox.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.growbox.screen.auth.auth.LogInDestination
import com.example.growbox.screen.auth.auth.LogInScreen
import com.example.growbox.screen.auth.auth.SignUpDestination
import com.example.growbox.screen.auth.auth.SignUpScreen
import com.example.growbox.screen.auth.auth.SplashDestination
import com.example.growbox.screen.auth.auth.SplashScreen



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

        // 1. SPLASH SCREEN
        composable(route = SplashDestination.route) {
            SplashScreen(
                onAuthSuccess = {
                    //  НІЧОГО НЕ РОБИМО.
                },
                onAuthFailure = {

                    navController.navigate(LogInDestination.route) {
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
    }
}

