package com.example.growbox.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.profile.components.ProfileHeaderCard
import com.example.growbox.screen.profile.components.ProfileMenuItem

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile_title
    override val showBottomBar = true
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToLogin: () -> Unit,
    onNavigateToChangeCrop: () -> Unit,
    onNavigateToMyHarvest: () -> Unit,
    onNavigateToHistoric: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val user by viewModel.userState.collectAsState()
    val crop by viewModel.cropState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
        )

        ProfileHeaderCard(
            user = user,
            crop = crop,
            email = viewModel.userEmail
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_24)))

        ProfileMenuItem(
            icon = Icons.Default.Sync,
            label = "Change Crop Type",
            onClick = { onNavigateToChangeCrop() }
        )

        ProfileMenuItem(
            icon = Icons.Default.Eco,
            label = "My Harvest",
            onClick = { onNavigateToMyHarvest() }
        )

        ProfileMenuItem(
            icon = Icons.Default.History,
            label = "Historic Data",
            onClick = { onNavigateToHistoric() }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        ProfileMenuItem(
            icon = Icons.Default.ExitToApp,
            label = "Log Out",
            isLogout = true,
            onClick = {
                viewModel.logout { onNavigateToLogin() }
            }
        )
    }
}
