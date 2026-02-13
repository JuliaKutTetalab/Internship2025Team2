package com.example.growbox.screen.bluetooth.connected

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.data.model.BluetoothDeviceInfo
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.bluetooth.components.GradientButton
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800

object ConnectedDeviceDestination: NavigationDestination {
    override val route = "connected_device"
    override val showBottomBar = false
    override val titleRes = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectedDeviceScreen(
    onNavigateBack: () -> Unit,
    onNextClick: () -> Unit
){
    // Поки Mock доки не зробимо ViewModel

    val connectedDevice = remember{
                BluetoothDeviceInfo(
                    name = "GrowBox",
                    address = "Femtolab Gro-06",
                    version = "Version 1.1.5"
                )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.connected_title),
                            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.description_bluetooth_screen))
                    }
                }
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
                modifier =Modifier.padding(top = dimensionResource(R.dimen.padding_large))
            ){
                Text(
                    text = connectedDevice.name,
                    fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = connectedDevice.address,
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    color = Green800
                )
                Text(
                    text = connectedDevice.version,
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    color = Gray999
                )

                Image(
                    painter = painterResource(R.drawable.ic_bluetooth_icon),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size_extra_huge))
                )

            }
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.spacing_large)))

            GradientButton(
                text = stringResource(R.string.connected_next_button),
                isEnabled = true,
                onClick = onNextClick,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_large)
                )
            )
        }
    }
}