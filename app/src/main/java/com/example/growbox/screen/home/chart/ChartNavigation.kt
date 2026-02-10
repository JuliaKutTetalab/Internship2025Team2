package com.example.growbox.screen.home.chart

import com.example.growbox.navigation.NavigationDestination

object ChartDestination: NavigationDestination {
    override val route =  "chart"
    const val chartTypeArg = "chartType"
    val routeWithArgs = "$route/{$chartTypeArg}"
    override val titleRes = null
    override val showBottomBar = true
}