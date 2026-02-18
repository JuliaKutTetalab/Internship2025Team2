package com.example.growbox.screen.home.chart


import com.example.growbox.R
import com.example.growbox.navigation.NavigationDestination

object ChartDestination: NavigationDestination {
    override val route =  "chart"
    const val chartTypeArg = "chartType"

    override val titleRes = R.string.change_crop_type_title
    override val showBottomBar = true

    const val cropIdArg = "cropId"


    val routeWithArgs = "$route/{$cropIdArg}/{$chartTypeArg}"


}