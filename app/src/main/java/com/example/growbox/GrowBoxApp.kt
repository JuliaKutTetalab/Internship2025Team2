package com.example.growbox

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.growbox.navigation.GrowBoxNavHost

@Composable
fun GrowBoxApp() {

    val navController = rememberNavController()

    GrowBoxNavHost(navController = navController)
}