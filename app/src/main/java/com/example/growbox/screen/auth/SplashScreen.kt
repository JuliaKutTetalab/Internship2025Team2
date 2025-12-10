package com.example.growbox.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import com.example.growbox.ui.theme.Black


@Composable
fun SplashScreen(
    onNavigateToSignUp: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToSignUp()
    }

    Box(
            modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_huge)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_giant)))

            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = "Plant icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_size_huge))
//
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

            Text(
                text = "Welcome to App",
                fontSize = dimensionResource(R.dimen.font_size_title_large).value.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}