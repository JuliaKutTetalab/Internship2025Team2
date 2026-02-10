package com.example.growbox.screen.home.components


import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults

import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.screen.home.HomeDestination
import com.example.growbox.screen.home.profile.ProfileDestination
import com.example.growbox.screen.settings.SettingsDestination
import com.example.growbox.ui.theme.GreenLight


@Composable
fun GrowBoxBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {

    val items = listOf(
        Triple(HomeDestination.route, R.drawable.ic_home_icon, R.string.home_title),
        Triple(SettingsDestination.route, R.drawable.ic_setting_icon, R.string.settings_title),
        Triple(ProfileDestination.route, R.drawable.ic_profile, R.string.profile_title),
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { (route, iconRes, label) ->
            val isSelected: Boolean = currentRoute == route
            Log.d("GrowBoxBottomBar", currentRoute.toString())
            Log.d("GrowBoxBottomBar", label.toString())

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != route) {
                        onNavigate(route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = stringResource(id = label),
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = label),
                        fontSize = 12.sp,

                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenLight,
                    selectedTextColor = Color(0xFF7ED957),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
