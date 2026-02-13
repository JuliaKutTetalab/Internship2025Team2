package com.example.growbox.screen.bluetooth.select_device.model

import com.example.growbox.data.model.BluetoothDeviceInfo

data class ConnectDeviseUiState (
    val devices: List<BluetoothDeviceInfo> = emptyList(),
    val selectedDevice: BluetoothDeviceInfo? = null,
    val isLoading: Boolean = false
)