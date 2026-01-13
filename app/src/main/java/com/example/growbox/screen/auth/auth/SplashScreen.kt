package com.example.growbox.screen.auth.auth

import android.util.Log
import android.window.SplashScreen
import androidx.browser.trusted.splashscreens.SplashScreenParamKey
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.ui.theme.Black
import androidx.compose.ui.res.stringResource

object SplashDestination : NavigationDestination {
    override val route = "splash_route"
    override val titleRes = R.string.splash_screen
}
@Composable
fun SplashScreen(
    onAuthSuccess: () -> Unit,
    onAuthFailure: () -> Unit
) {
    val viewModel: SplashScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = null)

    //ЗМІНА: Спостерігаємо за uiState
    val uiState by viewModel.uiState.collectAsState()



    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is SplashState.Ready -> {

                if (state.isLoggedIn) {
                    onAuthSuccess()
                } else {
                    onAuthFailure()
                }
            }
            is SplashState.Loading -> {
                //Нічого не робимо, чекаємо
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_plant),
            contentDescription = stringResource(R.string.content_description_plant_icon),
            tint = Color.Unspecified,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_size_huge))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        Text(
            text = stringResource(R.string.splash_welcome),
            fontSize = dimensionResource(R.dimen.font_size_title_large).value.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onAuthSuccess = {}, onAuthFailure = {})
}