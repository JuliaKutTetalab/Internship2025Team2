package com.example.growbox.navigation

interface NavigationDestination {
    val route: String
    val titleRes: Int?
    val showBottomBar: Boolean
}
