package com.example.growbox.screen.bluetooth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.data.model.BluetoothDeviceInfo
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800

@Composable
fun DeviceListItem(
    device: BluetoothDeviceInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
            Image(
                painter = painterResource(R.drawable.ic_bluetooth_icon),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_extra_large))
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))

        Column{
            Text(
                text = device.name,
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = device.address,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Green800
            )
            Text(
                text = device.version,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Gray999
            )
        }


    }
}