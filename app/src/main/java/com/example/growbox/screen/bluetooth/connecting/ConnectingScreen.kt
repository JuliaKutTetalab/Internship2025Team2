package com.example.growbox.screen.bluetooth.connecting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.growbox.screen.bluetooth.components.OutlinedGradientButton
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import kotlinx.coroutines.delay

object ConnectingDestination : NavigationDestination {
    override val route = "connecting"
    override val showBottomBar = false
    override val titleRes = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectingScreen(
    onCancel: () -> Unit,
    onConnected: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000)
        onConnected()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.connecting_title),
                            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.description_bluetooth_screen),
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.connecting_warning_text),
                    fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                    color = Gray999,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_large)),
                    color = GreenLight,
                    trackColor = Gray999
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

                Image(
                    painter = painterResource(R.drawable.ic_bluetooth_icon),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size_extra_huge))
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

                Text(
                    text = "GrowBox",
                    fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.connect_device_name),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    color = Green800
                )
                Text(
                    text = stringResource(R.string.connect_device_version),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    color = Gray999
                )
            }

            OutlinedGradientButton(
                text = stringResource(R.string.connecting_cancel_button),
                isEnabled = true,
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_large)
                    )
            )
        }
    }
}
