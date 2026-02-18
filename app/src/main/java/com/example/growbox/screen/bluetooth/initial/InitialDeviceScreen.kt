package com.example.growbox.screen.bluetooth.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.bluetooth.components.GradientButton
import com.example.growbox.ui.theme.Gray999

object InitialDeviceDestination : NavigationDestination {
    override val route = "initial_device"
    override val showBottomBar = false
    override val titleRes = null
}

@Composable
fun InitialDeviceScreen(
    onConnectClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.initial_device_title),
            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        Text(
            text = stringResource(R.string.initial_warning_text),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            color = Gray999,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.spacing_large)))

        Image(
            painter = painterResource(R.drawable.ic_bluetooth_icon),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.onboarding_icon_size_small))
        )

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.spacing_huge)))

        GradientButton(
            text = stringResource(R.string.initial_device_button),
            isEnabled = true,
            onClick = onConnectClick,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
    }
}
