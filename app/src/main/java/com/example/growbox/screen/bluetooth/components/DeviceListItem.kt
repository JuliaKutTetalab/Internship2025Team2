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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import androidx.compose.ui.graphics.Color
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800

@Composable
fun DeviceListItem(
    name: String,
    address: String,
    version: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_bluetooth_icon),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_extra_large))
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))

        Column {
            Text(
                text = name,
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = address,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Green800
            )
            Text(
                text = version,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Gray999
            )
        }
    }
}
