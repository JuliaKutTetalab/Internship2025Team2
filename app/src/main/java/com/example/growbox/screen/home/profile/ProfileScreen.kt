package com.example.growbox.screen.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.home.profile.components.ProfileHeaderCard
import com.example.growbox.screen.home.profile.components.ProfileMenuItem

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile_title
    override val showBottomBar = true
}
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToLogin: () -> Unit
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        ProfileHeaderCard(
            user = user,
            crop = crop,
            email = viewModel.userEmail
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileMenuItem(icon = Icons.Default.Sync, label = "Change Crop Type")
        ProfileMenuItem(icon = Icons.Default.Eco, label = "My Harvest")
        ProfileMenuItem(icon = Icons.Default.History, label = "Historic Data")

        Spacer(modifier = Modifier.height(8.dp))

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


//@Preview(showBackground = true, name = "Profile Screen Preview")
//@Composable
//fun ProfileScreenPreview() {
//    // Якщо у тебе є тема GrowBoxTheme, краще обгорнути в неї
//    Surface {
//        ProfileScreen()
//    }
//}

//@Preview(showBackground = true, name = "Header Card Preview")
//@Composable
//fun ProfileHeaderCardPreview() {
//    Box(modifier = Modifier.padding(16.dp)) {
//        ProfileHeaderCard()
//    }
//}