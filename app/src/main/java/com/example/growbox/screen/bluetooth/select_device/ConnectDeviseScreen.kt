package com.example.growbox.screen.bluetooth.select_device

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.data.model.BluetoothDeviceInfo
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.bluetooth.components.DeviceListItem

object ConnectDeviceDestination: NavigationDestination {
    override val route = "connect_device"
    override val showBottomBar = false
    override val titleRes = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectDeviceScreen(
    onNavigateBack: () -> Unit,
    onDeviseSelected: (BluetoothDeviceInfo) -> Unit
){
    // Поки Mock доки не зробимо ViewModel

    val mockDevices = remember{
        listOf(
            BluetoothDeviceInfo(
                R.string.app_name,
                R.string.connect_device_name,
                R.string.connect_device_version
                ),
            BluetoothDeviceInfo(
                R.string.app_name,
                R.string.connect_device_name,
                R.string.connect_device_version
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.connect_device_title),
                        fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                        fontWeight = FontWeight.Bold
                    )
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(mockDevices) { device ->
                DeviceListItem(
                    device = device,
                    onClick = {onDeviseSelected(device)}
                )

                HorizontalDivider()
            }
        }

    }
}